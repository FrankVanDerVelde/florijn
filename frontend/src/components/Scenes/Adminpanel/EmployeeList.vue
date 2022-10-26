<template>
  <div class="page-main-mw">
    <div class="flex flex-row md:flex-row p-4 ">
      <div class="side-bar w-[121px] flex-col justify-between">
        <side-bar-nav :side-bar-items="sideBarLinks"></side-bar-nav>
      </div>
      <div class="profile-container flex flex-row w-4/5">
        <button id="addbutton"
                class="bg-primary-500 font-bold border-[1px] h-[38px] md:w-auto rounded-md text-neutral-0">+ Werknemer
          toevoegen
        </button>
        <div class="container grow">
          <employee-list-details v-for="employee in employees" :key="employee.id"
                                 :employee="employee"></employee-list-details>
        </div>
      </div>
    </div>
  </div>


</template>

<script>

import SideBarNav from "../../Common/SideBarNav.vue";
import EmployeeListDetails from "./EmployeeListDetails.vue";

export default {
  name: "EmployeeList",
  components: {
    EmployeeListDetails,
    SideBarNav,
  },

  data() {
    return {
      sideBarLinks: [{
        icon: 'fa-solid fa-share-nodes',
        name: 'Klanten',
        href: 'customer-list',
      },
        {
          icon: 'fa-solid fa-user',
          name: 'Werknemers',
          href: 'employee-list',
        }],
      employee: [
        {
          id: 0,
          name: "Yuri S.",
          description: "Titel / Type werk komt hier te staan",
          logoSrc: "/src/assets/avatars/avatar1.avif",
        }, {
          id: 1,
          name: "Frank V.",
          description: "Titel / Type werk komt hier te staan",
          logoSrc: "/src/assets/avatars/avatar3.avif",
        }, {
          id: 2,
          name: "Thomas T.",
          description: "Titel / Type werk komt hier te staan",
          logoSrc: "/src/assets/avatars/avatar2.avif",
        }, {
          id: 3,
          name: "Martijn W.",
          description: "Titel / Type werk komt hier te staan",
          logoSrc: "/src/assets/avatars/avatar1.avif",
        }, {
          id: 4,
          name: "Milon B.",
          description: "Titel / Type werk komt hier te staan",
          logoSrc: "/src/assets/avatars/avatar2.avif",
        }, {
          id: 5,
          name: "Lukas G.",
          description: "Titel / Type werk komt hier te staan",
          logoSrc: "/src/assets/avatars/avatar3.avif",
        }
      ]
    }
  }

}
</script>

<style scoped>

.side-bar {
  margin-top: 50px;
  margin-right: 80px;
}

.container {
  margin-left: 20px;
  margin-top: 24px;
}

.profile-container {
  justify-content: flex-start;
}


</style>