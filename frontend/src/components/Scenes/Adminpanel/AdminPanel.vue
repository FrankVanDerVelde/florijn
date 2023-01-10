<template>
  <div class="page-main-mw pt-[5em] ">
    <div class="profile-container flex self-center">
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
  name: "AdminPanel",
  components: {
    SideBarNav
  },
  created() {
    if (localStorage.getItem("user") == null) {
      this.$router.push({name: "home"});
    }
  },
  data() {
    return {
      id: localStorage.getItem("id"),
      sideBarLinks: [
        {
          icon: 'fa-solid fa-share-nodes',
          name: 'Klanten',
          href: '/adminpanel/customer-list',
        },
        {
          icon: 'fa-solid fa-user',
          name: 'Specialisten',
          href: '/adminpanel/employee-list',
        }
      ],
    }
  }
}

</script>

<style scoped>

.side-bar {
  margin-top: 71px;
  margin-right: 80px;
  width: 200px;
}

</style>