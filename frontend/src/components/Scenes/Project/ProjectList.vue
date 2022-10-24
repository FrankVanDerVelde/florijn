<template>
  <div class="page-main-mw p-4">
    <div class="flex justify-center">
      <div class="profile-container flex flex-row self-center w-4/5">
        <div class="side-bar w-1/6">
          <div>Projecten</div>
          <div>Uren registratie</div>
          <div>Overeenkomsten</div>
        </div>
        <div class="container grow">

          <p class="" v-if="projects.length === 0">No assigned projects</p>

          <project-list-details v-for="project in projects" :key="project.id" :project="project"></project-list-details>

        </div>
      </div>
    </div>
  </div>
</template>

<script>

import ProjectListDetails from "./ProjectListDetails.vue";


export default {
  name: "ProjectList",
  components: {ProjectListDetails},

  data() {
    return {
      projects: [
        {
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        },{
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        },{
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/avatars/avatar1.png",
          participants: 6
        },{
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        },{
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        },{
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        }
      ]
    }
  }

}
</script>

<style scoped>

</style>