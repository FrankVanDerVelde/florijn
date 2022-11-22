<template>
  <div class="page-main-mw">
    <div class="flex gap-3 md:flex-row flex-col-reverse md:flex-row mt-4">
      <div class="w-full p-4">
        <h1>{{ newProject ? "Nieuw" : "Wijzig" }} project</h1>
        <hr>
        <form class="pt-2">
          <label class="w-full">
            <div>Titel</div>
            <input v-model="project.title"
                   type="text"
                   placeholder="Project titel"
                   class="w-full border border-neutral-300 p-2 rounded rounded-[4px]">
          </label>
          <label class="w-full mt-3">
            <div>Omschrijving</div>
            <input v-model="project.description"
                   type="text"
                   placeholder="Omschrijving"
                   class="w-full border border-neutral-300 p-2 rounded rounded-[4px]">
          </label>
          <label class="w-full mt-3">
            <div>Klant</div>
            <select class="w-full">
              <option>ING</option>
              <option>Albert Heijn</option>
            </select>
          </label>
          <label class="w-full mt-3">
            <div>Logo</div>
            <input type="file">
          </label>
        </form>
      </div>
      <div class="w-full p-4">
        <h2>Preview</h2>
        <ProjectLayout :project-info="project" :preview="true"/>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectLayout from "./ProjectLayout.vue";

export default {
  name: "CreateProject",
  components: {ProjectLayout},
  inject: ['projectFetchService'],

  async created() {
    if (!this.newProject) {
      this.project = await this.projectFetchService.fetchJson(`/${this.projectId}`);
    } else {
      this.project = this.getSampleProject();
    }

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
      description: ""
    }
  },

  methods: {
    getSampleProject() {
      return {
        title: "Nieuw project",
        description: "Hier komt de beschrijving van het project.",
        participants: [],
      }
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
//border-bottom: 1px solid var(--neutral-100);
}

label {
  display: block;
  font-size: 24px;
  color: #000;
  font-family: Inter, sans-serif;
  font-weight: 600;
}

input {
  font-weight: 400;
  font-size: 18px;
}
</style>