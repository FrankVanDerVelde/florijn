<template>
  <div class="page-main-mw">
    <div class="p-4 ">
      <div class="profile-container flex-col">
        <div class="buttoncontainer">
          <router-link to="/adminpanel/add-employee">
            <button id="addbutton" class="bg-primary-500 font-bold border-[1px] h-[38px] rounded-md text-neutral-0" @click="navigate" role="link">+ Werknemer toevoegen</button>
          </router-link>
        </div>
        <div class="container grow">
          <employee-list-details v-for="employee in employee" :key="employee.id"
                                 :employee="employee"></employee-list-details>
        </div>
      </div>

    </div>
  </div>


</template>

<script>

import EmployeeListDetails from "./EmployeeListDetails.vue";

export default {
  name: "EmployeeList",
  components: {
    EmployeeListDetails,
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
      employees: [
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



.buttoncontainer{
  display: flex;
  justify-content: flex-end;
}

#addbutton{
  width: 198px;
}




</style>