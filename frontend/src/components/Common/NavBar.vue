<template>
  <div class="sticky top-0 z-50 h-[60px] bg-neutral-0 border-b border-b-neutral-200 shadow shadow">
    <div class="page-main-mw h-full hidden md:flex flex-row items-center justify-between">
      <div class="flex items-center h-full">
        <img class="h-full p-4 mr-6 object-contain h-full" :src="logo" alt="Business Logo">

        <div class="flex gap-7 h-full w-fit flex items-center">
          <nav-item v-for="item in links" :key="item.name" :data="item"/>
        </div>
      </div>
      <div class="h-full pr-4 pl-4">
        <nav-item class="w-fit" :data="this.staticLink"/>
      </div>
    </div>
    <div class="page-main-mw p-4 h-full flex md:hidden flex-row items-center justify-between">
      <font-awesome-icon v-if="!menuExpanded" size="xl" fixed-width icon="bars" class="menuExpansionIcon" @click="toggleMenu"/>
      <font-awesome-icon v-else size="xl" icon="times" fixed-width class="menuExpansionIcon" @click="toggleMenu"/>

      <img class="h-full w-fit" :src="logo" alt="Business Logo">
      <nav-item :data="this.staticLink"/>
    </div>

    <div v-if="menuExpanded" class="page-main-mw flex flex-col p-4 small-nav z-40 md:hidden">
      <nav-item v-for="item in links" :key="item.name" :data="item" :small="true" />
    </div>
  </div>
</template>

<script>
import logo from '../../assets/florijn_logo.png';
import NavItem from "./NavItem.vue";

export default {
  name: 'NavBar',
  components: {NavItem},
  setup() {
    return {
      logo,
    };
  },

  watch: {
    '$route'() {
      this.menuExpanded = false;
    }
  },

  data() {
    return {
      menuExpanded: false,
      links: [
        {
          name: 'Home',
          link: '/home'
        }, {
          name: 'Profiel',
          link: '/profile'
        }, {
          name: 'Project Overzicht',
          link: '/projects/1'
        }, {
          name: 'Projecten',
          link: '/projects'
        },
        {
          name: 'Admin',
          link: '/adminpanel'
        }, {
        name: 'Deelnemer toevoegen',
          link: '/add-participants'
        }
      ],
      staticLink: {
        name: 'Log in',
        link: '/login'
      }
    }
  },

  methods: {
    toggleMenu() {
      this.menuExpanded = !this.menuExpanded;

      document.body.style.overflow = this.menuExpanded ? 'hidden' : 'auto';
    }
  }
};

</script>

<style scoped>
.menuExpansionIcon {
  cursor: pointer;
  color: var(--neutral-300);
}

.small-nav {
  position: sticky;
  top: 60px;
  left: 0;
  background-color: #fff;
  border-top: 1px solid var(--neutral-100);
  height: calc(100vh - 60px);
  width: 100vw;
}

</style>