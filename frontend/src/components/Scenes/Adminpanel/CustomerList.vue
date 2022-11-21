<template>
  <div class="page-main-mw">
    <div class="p-4 ">
      <div class="profile-container flex-col">
        <div class="buttoncontainer">
          <router-link to="/adminpanel/add-client">
            <button id="addbutton" class="bg-primary-500 font-bold border-[1px] h-[38px] w-[180px] rounded-md text-neutral-0" @click="navigate" role="link">+ Klant toevoegen</button>
          </router-link>
        </div>
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

export default {
  name: "CustomerList",
  components: {
    CustomerListDetails
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
      customers: [
        {
          id: 0,
          title: "ING Banking",
          description: "Bedrijf informatie komt hier te staan",
          logoSrc: "/src/assets/ING-Bankieren-icoon.webp",
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



.buttoncontainer{
  display: flex;
  justify-content: flex-end;
}




</style>