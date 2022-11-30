

<template>
    <div class="page-main-mw p-4 pt-[5em] flex">
        <div class="profile-container flex self-center w-4/5">
            <div class="mr-[4em]">
                <SideBarNav :sideBarItems="sideBarLinks"></SideBarNav>
            </div>

            <div class="grow">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
  
<style scoped>

</style>

<script>
export default {
    name: "Profile",
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
                icon: 'fa-solid fa-user',
                name: 'profiel',
                href: '/profile/personal-info',
            },
            {
                icon: 'fa-solid fa-lightbulb',
                name: 'skills',
                href: '/profile/skills',
            },
            {
                icon: 'fa-solid fa-calendar-days',
                name: 'Beschikbaarheid',
                href: '/profile/available-hours',
            },
            {
                icon: 'fa-solid fa-calendar-week',
                name: 'Uren registratie',
                href: '/profile/hour-registration',
            },
        ]
        }
    }
}

import SideBarNav from "../../Common/SideBarNav.vue";

</script>