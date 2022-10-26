<template>
  <div class="page-main-mw pt-[5em] flex">
    <div class="list-container flex self-center w-full">
      <div class="mr-[4em]">
        <SideBarNav :sideBarItems="sideBarLinks"></SideBarNav>
      </div>

      <div class="grow">
        <project-list-details v-for="project in projects" :key="project.id" :project="project"></project-list-details>
      </div>


    </div>


  </div>


</template>

<script>

import ProjectListDetails from "./ProjectListDetails.vue";
import SideBarNav from "../../Common/SideBarNav.vue";


export default {
  name: "ProjectList",
  components: {ProjectListDetails, SideBarNav},

  data() {
    return {
      sideBarLinks: [{
        icon: 'fa-solid fa-user',
        name: 'projecten',
        href: '/project-list',
      }],
      projects: [
        {
          id: 0,
          title: "ING Banking Web Application",
          description: "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
          logoSrc: "/src/assets/ING-Bankieren-icoon.webp",
          participants: 3
        }, {
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        }, {
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        }, {
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        }, {
          id: 0,
          title: "Project EWA",
          description: "Website ontwikkeling voor Florijn",
          logoSrc: "/src/assets/logo-small.png",
          participants: 6
        }, {
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