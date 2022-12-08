<template>
  <div class="page-main-mw">
    <div class="p-4 ">
      <div class="profile-container flex-col">
        <div class="buttoncontainer">
          <router-link to="/adminpanel/add-specialist">
            <button id="addbutton" class="bg-primary-500 font-bold border-[1px] h-[38px] rounded-md text-neutral-0" @click="navigate" role="link">+ Specialist toevoegen</button>
          </router-link>
        </div>
        <div class="container grow">
          <employee-list-details v-for="employee in employees" :key="employee.id"
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
  inject: ['fetchService'],


  async created() {
    this.employees = await this.fetchService.fetchJson(`/users/role/specialist`)
  },

  data() {
    return {
      employees: {},
      sideBarLinks: [{
        icon: 'fa-solid fa-share-nodes',
        name: 'Klanten',
        href: 'customer-list',
      },
        {
          icon: 'fa-solid fa-user',
          name: 'Specialisten',
          href: 'employee-list',
        }],
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