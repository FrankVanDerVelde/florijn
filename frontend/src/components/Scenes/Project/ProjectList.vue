<template>
  <div class="page-main-mw p-4 pt-[5em] flex">
    <div class="list-container flex self-center w-full">
      <div class="mr-[4em]">
        <SideBarNav :sideBarItems="sideBarLinks"></SideBarNav>
      </div>

      <div class="grow">
        <Searchbar placeholder="Zoek een project..."
                   v-model="searchQuery"
                   @submit="fetchProjects"/>

        <div v-if="projects.length === 0" class="text-app_red-400">Er konden geen projecten worden gevonden met het gegeven zoekopdracht.</div>

        <project-list-details v-for="project in projects" :key="project.id" :project="project"/>
      </div>
    </div>
  </div>
</template>

<script>

import ProjectListDetails from "./ProjectListDetails.vue";
import SideBarNav from "../../Common/SideBarNav.vue";
import Searchbar from "../../Common/Searchbar.vue";

export default {
  name: "ProjectList",
  components: {Searchbar, ProjectListDetails, SideBarNav},
  inject: ['projectFetchService'],

  created() {
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