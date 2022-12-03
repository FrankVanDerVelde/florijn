<template>
  <div class="page-main-mw">
    <div class="flex gap-3 md:flex-row flex-col-reverse md:flex-row mt-4">
      <div class="w-full p-4 pt-0 sm:pt-4 sm:pb-0 md:pt-4">
        <div class="hidden md:block">
          <h1 class="text-3xl leading-tight mb-2 font-bold">{{ newProject ? "Nieuw" : "Wijzig" }} project</h1>
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

          <div v-if="newProject" class="label w-full mt-3">
            <div>Klant</div>

            <ClientSelect :clients="clients" v-model="project.client"/>
          </div>
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

          <div v-if="!newProject" class="mt-6">
            <h2 class="font-normal text-app_red-400 text-2xl mb">Danger Zone</h2>

            <div class="mt-2 border border-app_red-200 rounded-md">
              <ul>
                <DangerZoneRow @click="modals.ownership=true" title="Eigendom overdragen" button="Overdragen"
                               description="Draag het eigendom van dit project over aan een andere klant."/>
                <DangerZoneRow @click="modals.archive=true" title="Archiveer dit project" button="Archiveer"
                               description="Markeer dit project als gearchiveerd. Gearchiveerde projecten zullen niet actief in de project lijsten worden weergegeven en bepaalde rechten zullen worden gelimiteerd."/>
              </ul>
            </div>

            <Modal @close="modals.ownership=false" title="Eigendom overdragen" :is-open="modals.ownership">
              <p class="text-sm text-gray-600">
                Draag het eigendom van dit project over aan een andere klant.
              </p>
              <p v-if="true" class="text-sm mt-2 text-gray-600 italic">
                Let op: je bent eigenaar van dit project. Project eigendom overdragen naar een andere klant zal je rechten tot dit project intrekken.
              </p>

              <label class="mt-3">Nieuwe klant</label>
              <ClientSelect :clients="clients" v-model="project.client"/>
            </Modal>

            <Modal @close="modals.archive=false" title="Project archiveren" :is-open="modals.archive">
              <p class="text-sm text-gray-600">
                Markeer dit project als gearchiveerd.
              </p>
              <p class="text-sm text-gray-600 mt-2">
                Gearchiveerde projecten zullen niet worden weergegeven in project lijsten en bepaalde rechten zullen
                worden gelimiteerd.
              </p>

              <label class="mt-3">Nieuwe klant</label>
              <ClientSelect :clients="clients" v-model="project.client"/>
            </Modal>

          </div>
        </form>
      </div>
      <div class="w-full p-4">
        <div class="md:hidden mb-4">
          <h1 class="text-3xl leading-tight mb-2 font-bold">{{ newProject ? "Nieuw" : "Wijzig" }} project</h1>
          <hr>
          <router-link :to="{name: 'project-overview'}"
                       class="muted error !mt-[-10px] block mb-2"
                       v-if="!newProject">
            &#60; Terug naar project overzicht
          </router-link>
        </div>
        <h2 class="font-semibold uppercase tracking-widest text-[20px] mb-2">Preview</h2>
        <ProjectLayout v-if="project != null" :project-info="project" :preview="true"/>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectLayout from "./ProjectLayout.vue";
import PrimaryButton from "../../../Common/PrimaryButton.vue";
import ClientSelect from "../ClientSelect.vue";
import DangerZoneRow from "../DangerZoneRow.vue";
import Modal from "../../../Common/Modal.vue";

export default {
  name: "CreateProject",
  components: {Modal, DangerZoneRow, ClientSelect, PrimaryButton, ProjectLayout},
  inject: ['projectFetchService', 'fetchService'],

  computed: {
    user() {
      return JSON.parse(localStorage.getItem('user'));
    }
  },

  async created() {
    if (!this.newProject) {
      this.project = await this.projectFetchService.fetchJson(`/${this.projectId}`);

      if (this.project == null) {
        this.$router.push({name: 'projects'});
        return;
      }

    } else {
      this.project = this.getSampleProject();
    }

    this.clients = await this.fetchService.fetchJson(`/users/role/client`);
    if (!this.newProject) this.injectProjectInfo();

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
      project: null,
      title: "",
      description: "",
      logoFile: null,
      clients: [],
      errors: {},
      modals: {
        ownership: false,
        archive: false,
      }
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
    async updateLogo(event) {
      if (event.target.files.length === 0) {
        this.logoFile = null;
        delete this.project.logoSrc;
        return;
      }

      const fileExtension = event.target.files[0].name.split('.').pop();
      if (!['svg', 'png', 'webp', 'jpg', 'jpeg'].includes(fileExtension)) {
        this.errors.logo = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
        return;
      }

      this.logoFile = event.target.files[0];
      this.project.logoSrc = `${await this.getBase64(event.target.files[0])}`;
    },
    injectProjectInfo() {
      this.title = this.project.title;
      this.description = this.project.description;

      if (this.project.client != null) {
        let filter = this.clients.filter(c => c.id === this.project.client?.id);
        console.log(this.clients)
        if (filter.length > 0) this.project.client = filter[0];
      }

    },
    validate() {
      this.errors = {};

      if (this.title.trim().length === 0) {
        this.errors.title = "De titel mag niet leeg zijn.";
      }
      if (this.description.trim().length === 0) {
        this.errors.description = "De omschrijving mag niet leeg zijn.";
      }
      if (this.project.client?.id == null) {
        this.errors.client = "Er is geen klant geselecteerd.";
      }

      return Object.keys(this.errors).length === 0;
    },
    async saveProject() {
      if (!this.validate()) return;

      console.log(this.logoFile);

      const formData = new FormData();
      formData.append('title', this.title);
      formData.append('description', this.description);
      formData.append('client', this.project.client.id);
      if (this.logoFile != null) formData.append('logoFile', this.logoFile);

      let response;
      if (this.newProject) {
        response = await this.projectFetchService.fetchJsonFile('/create', "POST", formData);
      } else {
        response = await this.projectFetchService.fetchJsonFile(`/${this.projectId}/update`, "PUT", formData);
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

label, .label {
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