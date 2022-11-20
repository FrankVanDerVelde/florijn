<template>
  <div class="page-main-mw pt-[5em] flex">
    <div class="profile-container flex self-center w-4/5">
      <div class="side-bar w-[121px] flex-col justify-between">
        <SideBarNav :sideBarItems="sideBarLinks"></SideBarNav>
      </div>
      <div class="grow">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import SideBarNav from "../../Common/SideBarNav.vue";

export default {
  name: "Adminpanel",

  components: {
    SideBarNav
  },
  created() {
    if (this.id === "null") {
      this.$router.push("/home");
    }
  },
  data() {
    return {
      id: localStorage.getItem("id"),
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
    }
  }
}

</script>

<style scoped>

.side-bar {
  margin-top: 50px;
  margin-right: 80px;
}

</style>