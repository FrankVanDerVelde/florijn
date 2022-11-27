<template>
  <div class="page-main-mw">
    <div class="flex gap-3 md:flex-row flex-col-reverse md:flex-row mt-4">
      <div class="w-full sm:pt-0 md:pt-4 p-4">
        <div class="hidden md:block">
          <h1>{{ newProject ? "Nieuw" : "Wijzig" }} project</h1>
          <hr>
          <router-link :to="{name: 'project-overview'}"
                       class="muted error !mt-[-10px] block mb-2"
                       v-if="!newProject">
            &#60; Terug naar project overzicht
          </router-link>
        </div>
        <form class="pt-2" @submit.prevent="saveProject">
          <label class="w-full">
            <div>Titel</div>
            <input @input="e => updateTitle(e)"
                   :value="title"
                   type="text"
                   placeholder="Project titel"
                   :class="{'error': 'title' in errors}">
          </label>
          <div class="muted error" v-if="'title' in errors">{{ errors.title }}</div>

          <label class="w-full mt-3">
            <div>Omschrijving</div>
            <input @input="e => updateDescription(e)" :value="description"
                   type="text"
                   placeholder="Omschrijving"
                   :class="{'error': 'description' in errors}">
          </label>
          <div class="muted error" v-if="'description' in errors">{{ errors.description }}</div>

          <label class="w-full mt-3">
            <div>Klant</div>
            <select
                class="w-full"
                :class="{'error': 'client' in errors}"
                @change="e => updateClient(e)"
                required>
              <option v-if="!('id' in project?.client ?? {})" :value="null" disabled :selected="projectId == -1">Selecteer een klant</option>
              <option v-for="client in clients"
                      :key="client.id"
                      :value="client.id"
                      :selected="client.id === clientId">
                {{ client.name }}
              </option>
            </select>
          </label>
          <div class="muted error" v-if="'client' in errors">{{ errors.client }}</div>

          <label class="w-full mt-3">
            <div>Logo</div>
            <input @change="e => updateLogo(e)"
                   type="file"
                   accept=".svg,.png,.webp,jpg,.jpeg"
                   class="w-full"
                   :class="{'error': 'logo' in errors}">
          </label>
          <div class="muted error" v-if="'logo' in errors">{{ errors.logo }}</div>

          <div class="muted">Het logo zal niet worden verwerkt voordat het project is {{ newProject ? "aangemaakt" : "opgeslagen" }}.
            Het huidige logo zal worden gebruikt als er geen logo wordt geüpload.
          </div>

          <div class="flex flex-col items-end mt-5">
            <PrimaryButton type="submit" :title="newProject ? 'Maak project aan' : 'Update project'"/>
            <div class="muted error" v-if="'updating' in errors">{{ errors.updating }}</div>
          </div>
        </form>
      </div>
      <div class="w-full p-4">
        <div class="md:hidden">
          <h1>{{ newProject ? "Nieuw" : "Wijzig" }} project</h1>
          <hr>
          <router-link :to="{name: 'project-overview'}"
                       class="muted error !mt-[-10px] block mb-2"
                       v-if="!newProject">
            &#60; Terug naar project overzicht
          </router-link>
        </div>
        <h2>Preview</h2>
        <ProjectLayout :project-info="project" :preview="true"/>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectLayout from "./ProjectLayout.vue";
import PrimaryButton from "../../../Common/PrimaryButton.vue";

export default {
  name: "CreateProject",
  components: {PrimaryButton, ProjectLayout},
  inject: ['projectFetchService', 'fetchService'],

  async created() {
    console.log("projectId:", this.projectId);

    if (!this.newProject) {
      this.project = await this.projectFetchService.fetchJson(`/${this.projectId}`);

      if (this.project == null) {
        this.$router.push({name: 'projects'});
        return;
      }

      this.injectProjectInfo();
    } else {
      this.project = this.getSampleProject();
    }

    this.clients = await this.fetchService.fetchJson(`/users/role/client`);

    // when a non-existing project is requested, redirect to the /projects page.
    if (this.project == null) {
      this.$router.push({name: 'projects'});
      return;
    }
  },

  props: {
    projectId: {
      type: String,
      default: "-1"
    },
    newProject: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      project: {},
      title: "",
      description: "",
      logoSrc: "",
      clients: [],
      clientId: -1,
      errors: {}
    }
  },

  methods: {
    getSampleProject() {
      return {
        title: "Nieuw project",
        description: "Hier komt de beschrijving van het project.",
        participants: [],
        client: {},
      }
    },
    updateTitle(event) {
      this.title = event.target.value.trim();
      if (this.title.length > 0) {
        delete this.errors.title;
      }

      this.project.title = this.title.length > 0 ? this.title : "Nieuw project";
    },
    updateDescription(event) {
      this.description = event.target.value.trim();
      if (this.description.length > 0) {
        delete this.errors.description;
      }

      this.project.description = this.description.length > 0 ? this.description : "Hier komt de beschrijving van het project.";
    },
    async updateClient(event) {
      this.clientId = event.target.value;

      this.project.client = await this.fetchService.fetchJson(`/user/${this.clientId}`);
      if (this.project.client == null) {
        this.errors.client = "Klant kon niet gevonden worden.";
      } else {
        delete this.errors.client;
      }
    },
    async updateLogo(event) {
      if (event.target.files.length === 0) {
        delete this.project.logoSrc;
        return;
      }

      const fileExtension = event.target.files[0].name.split('.').pop();
      if (!['svg', 'png', 'webp', 'jpg', 'jpeg'].includes(fileExtension)) {
        this.errors.logo = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
        return;
      }

      const formData = new FormData();
      formData.append('file', event.target.files[0]);
      formData.append('name', `projects/logo-${this.project.id}.${fileExtension}`);

      const res = await this.fetchService.fetchPostFile(`/assets/upload`, formData);
      console.log(res);

      this.logoSrc = `${await this.getBase64(event.target.files[0])}`;
      this.project.logoSrc = this.logoSrc;
    },
    injectProjectInfo() {
      this.title = this.project.title;
      this.description = this.project.description;
      this.clientId = this.project.client?.id ?? -1;
      this.logoSrc = this.project.logoSrc;
    },
    validate() {
      this.errors = {};

      if (this.title.trim().length === 0) {
        this.errors.title = "De titel mag niet leeg zijn.";
      }
      if (this.description.trim().length === 0) {
        this.errors.description = "De omschrijving mag niet leeg zijn.";
      }
      if (this.clientId === -1) {
        this.errors.client = "Er is geen klant geselecteerd.";
      }

      return Object.keys(this.errors).length === 0;
    },
    async saveProject() {
      if (!this.validate()) return;

      const body = {
        title: this.title,
        description: this.description,
        client: this.clientId,
        logoSrc: this.logoSrc == null || this.logoSrc === "" ? null : this.logoSrc
      };

      let response;
      if (this.newProject) {
        response = await this.projectFetchService.fetchJsonPost('/create', body);
      } else {
        response = await this.projectFetchService.fetchJsonMethod(`/${this.projectId}/update`, "PUT", body);
      }

      if (response == null) {
        this.errors.updating = `Er is een fout opgetreden tijdens het ${this.newProject ? "aanmaken" : "opslaan"} van het project.`;
      } else {
        this.$router.push(`/projects/${response.id}`);
      }
    },
    async getBase64(file) {
      // convert javascript file to base64 string
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
      });
    }
  }
}
</script>

<style scoped>
h1, h2 {
  font-weight: bold;
  color: var(--neutral-900);
  font-size: 32px;
  margin-bottom: 18px;
  line-height: 1.2;
}

h2 {
  font-size: 26px;
}

hr {
  color: var(--neutral-100);
  height: 20px;
//border-bottom: 1px solid var(--neutral-100);
}

.muted {
  font-size: 14px;
  line-height: 20px;
  margin-top: 8px;
  color: #5e6672;
}

.muted.error {
  margin-top: 4px;
  color: var(--app_red-400)
}

label {
  display: block;
  font-size: 1rem;
  line-height: 1.25rem;
  color: rgb(55, 65, 81);
  font-family: Inter, sans-serif;
  font-weight: bold;
}

input, select {
  width: 100%;
  border-color: rgb(209, 213, 219);
  --tw-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
  --tw-shadow-colored: 0 1px 2px 0 0 0 #0000;
  box-shadow: var(--tw-ring-offset-shadow, 0 0 #0000), 0 0 #0000, var(--tw-shadow);
  border-radius: 0.375rem;
  margin-top: .25rem;
  background: #fff;
  border-width: 1px;
  padding: 0.5rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5rem;
  font-weight: 400;
}

input.error, select.error {
  border-color: var(--app_red-300);
}
</style>