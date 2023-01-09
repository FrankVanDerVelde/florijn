<template>
    <div class="page-main-mw p-4 md:pt-[5em] flex">
        <div class="profile-container flex self-center w-full flex-col md:flex-row">
            <div class="md:mr-[4em] mb-4 md:mb-0 justify-center md:justify-start">
                <SideBarNav :sideBarItems="sideBarLinks.filter(link => link.roles.includes(userRole))"></SideBarNav>
            </div>
            <div class="grow page-content">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
  
<style scoped>
.page-main-mw {
    margin: 0 auto;
    max-width: 1230px;
}

.page-content {
  max-width: 715px;
}
</style>

<script>
export default {
  name: "Profile",
  components: {
    SideBarNav
  },
    data() {
        return {
          id: localStorage.getItem("id"),
            sideBarLinks: [{
                icon: 'fa-solid fa-user',
                name: 'profiel',
                href: '/profile/personal-info',
                roles: ["SPECIALIST", "ADMIN", "CLIENT"]
            },
            {
                icon: 'fa-solid fa-file',
                name: 'resume',
                href: '/profile/resume',
                roles: ["SPECIALIST"]
            },
            {
                icon: 'fa-solid fa-lightbulb',
                name: 'skills',
                href: '/profile/skills',
                roles: ["SPECIALIST"]
            },
            {
                icon: 'fa-solid fa-calendar-days',
                name: 'Beschikbaarheid',
                href: '/profile/available-hours',
                roles: ["SPECIALIST"]
            },
            {
                icon: 'fa-solid fa-calendar-week',
                name: 'Uren registratie',
                href: '/profile/hour-registration',
                roles: ["SPECIALIST"]
            },
        ],
        userRole: JSON.parse(localStorage.getItem("user")).role
        }
    }
}

import SideBarNav from "../../Common/SideBarNav.vue";

</script>