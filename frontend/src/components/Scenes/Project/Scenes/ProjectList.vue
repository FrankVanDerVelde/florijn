<template>
  <div class="page-main-mw p-4 pt-[3em] flex flex-col">
    <h1 class="text-3xl mb-3 font-bold">Projecten</h1>

    <Searchbar placeholder="Zoek een project..."
               v-model="searchQuery"
               @submit="fetchProjects"/>

    <div v-if="projects.length === 0" class="text-app_red-400">Er konden geen projecten gevonden met de gegeven
      zoekopdracht.
    </div>

    <div class="flex flex-col gap-3">
      <project-list-details v-for="project in projects" :key="project.id" :project="project"/>
    </div>
  </div>
</template>

<script>

import ProjectListDetails from ".././ProjectListDetails.vue";
import Searchbar from "../../../Common/Searchbar.vue";

export default {
  name: "ProjectList",
  components: {Searchbar, ProjectListDetails},
  inject: ['projectFetchService'],

  created() {
    if (localStorage.getItem("user") == null) {
      this.$router.push({name: "home"});
      return;
    }
    this.fetchProjects();
  },

  methods: {
    async fetchProjects() {
      // making sure that they can't send another request before the previous one is finished
      if (this.loadingProjects) return;

      this.loadingProjects = true;

      const search = encodeURIComponent(this.searchQuery);
      this.projects = await this.projectFetchService.fetchJson(`?query=${search}`);

      this.loadingProjects = false;
    }
  },

  data() {
    return {
      projects: [],
      searchQuery: "",
      loadingProjects: false,
      sideBarLinks: [{
        icon: 'fa-solid fa-user',
        name: 'projecten',
        href: '/projects',
      }],
    }
  }

}
</script>