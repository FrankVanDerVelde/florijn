<template>
  <div class="page-main-mw">
    <div class="flex flex-row md:flex-row p-4 ">
      <div class="side-bar w-[121px] flex-col justify-between">
        <side-bar-nav :side-bar-items="sideBarLinks"></side-bar-nav>
      </div>
      <div class="profile-container flex flex-row w-4/5">
          <button id="addbutton" class="bg-primary-500 font-bold border-[1px] h-[38px] w-[180px] rounded-md text-neutral-0">+ Klant toevoegen</button>
        <div class="container grow">
          <customer-list-details v-for="customer in customers" :key="customer.id"
                                 :customer="customer"></customer-list-details>
        </div>
      </div>

    </div>
  </div>


</template>

<script>

import CustomerListDetails from "./CustomerListDetails.vue";
import SideBarNav from "../../Common/SideBarNav.vue";

export default {
  name: "CustomerList",
  components: {
    SideBarNav,
    CustomerListDetails
  },

  data() {
    return {
      sideBarLinks: [{
        icon: 'fa-solid fa-share-nodes',
        name: 'Klanten',
        href: '/adminpanel/customer-list',
      },
        {
          icon: 'fa-solid fa-user',
          name: 'Werknemers',
          href: '/adminpanel/employee-list',
        }],
      customers: [
        {
          id: 0,
          title: "Bedrijf EWA",
          description: "Bedrijf informatie komt hier te staan",
          logoSrc: "/src/assets/logo-small.png",
        }, {
          id: 1,
          title: "Bedrijf EWA",
          description: "Bedrijf informatie komt hier te staan",
          logoSrc: "/src/assets/logo-small.png",
        }, {
          id: 2,
          title: "Bedrijf EWA",
          description: "Bedrijf informatie komt hier te staan",
          logoSrc: "/src/assets/logo-small.png",
        }, {
          id: 3,
          title: "Bedrijf EWA",
          description: "Bedrijf informatie komt hier te staan",
          logoSrc: "/src/assets/logo-small.png",
        }, {
          id: 4,
          title: "Bedrijf EWA",
          description: "Bedrijf informatie komt hier te staan",
          logoSrc: "/src/assets/logo-small.png",
        }, {
          id: 5,
          title: "Bedrijf EWA",
          description: "Bedrijf informatie komt hier te staan",
          logoSrc: "/src/assets/logo-small.png",
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

.profile-container{
  justify-content: flex-start;
}


</style>