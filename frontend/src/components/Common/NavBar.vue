<template>
  <div class="sticky top-0 z-50 h-[60px] bg-neutral-0 border-b border-b-neutral-200 shadow shadow">
    <div class="page-main-mw h-full hidden md:flex flex-row items-center justify-between">
      <div class="flex items-center h-full">
        <router-link :to="{ name: 'home' }" class="h-full">
          <img class="h-full p-4 mr-6 object-contain h-full" :src="logo" alt="Business Logo">
        </router-link>

        <div class="flex gap-7 h-full w-fit flex items-center">
          <nav-item v-for="item in links" :key="item.name" :data="item"/>
        </div>
      </div>
      <div class="h-full pr-4 pl-4 flex items-center">
        <nav-item v-if="user == null" class="w-fit" :data="this.staticLink"/>
        <ProfilePopup v-else/>
      </div>
    </div>
    <div class="page-main-mw p-4 h-full flex md:hidden flex-row items-center justify-between">
      <div v-if="this.links.length > 0">
        <font-awesome-icon v-if="!menuExpanded" size="xl" fixed-width icon="bars" class="menuExpansionIcon"
                           @click="toggleMenu"/>
        <font-awesome-icon v-else size="xl" icon="times" fixed-width class="menuExpansionIcon" @click="toggleMenu"/>
      </div>

      <img class="h-full w-fit" :src="logo" alt="Business Logo">

      <nav-item v-if="user == null" class="w-fit" :data="this.staticLink"/>
      <ProfilePopup v-else/>

      <!--      <nav-item :data="this.staticLink"/>-->
    </div>

    <div v-if="menuExpanded && links.length > 0" class="page-main-mw flex flex-col p-4 small-nav z-40 md:hidden">
      <nav-item v-for="item in links" :key="item.name" :data="item" :small="true"/>
    </div>
  </div>
</template>

<script>
import logo from '../../assets/florijn_logo.png';
import NavItem from "./NavItem.vue";
import ProfilePopup from "./ProfilePopup.vue";

export default {
  name: 'NavBar',
  components: {ProfilePopup, NavItem},
  setup() {
    return {
      logo,
    };
  },

  watch: {
    '$route'() {
      this.menuExpanded = false;
      this.user = JSON.parse(localStorage.getItem("user"));
      this.isLoggedIn();
    }
  },
  data() {
    return {
      menuExpanded: false,
      links: [],
      staticLink: {
        name: 'Log in',
        link: {
          name: 'login'
        }
      },
      user: JSON.parse(localStorage.getItem("user")),
    }
  },

  methods: {
    toggleMenu() {
      this.menuExpanded = !this.menuExpanded;
      document.body.style.overflow = this.menuExpanded ? 'hidden' : 'auto';
    },
    isLoggedIn() {
      if (this.user == null) {
        this.links = [];
        this.staticLink = {
          name: 'Log in',
          link: {
            name: 'login'
          }
        }
      } else {
        this.links = [
          {
            name: 'Home',
            link: {
              name: 'home'
            }
          },
          {
            name: 'Projecten',
            link: {
              name: 'projects'
            }
          },
        ];

        switch (this.user.role) {
          case "ADMIN":
            this.links.push(
                {
                  name: 'Admin',
                  link: {
                    name: 'admin-customer-list'
                  }
                }, {
                  name: 'Nieuw project',
                  link: {
                    name: 'new-project'
                  }
                }
            );
            break;
          case "SPECIALIST":
            this.links.push({
              name: 'Profiel',
              link: {
                name: 'profile'
              }
            });
            break;
          case "CLIENT":
            break;
        }
        this.staticLink = {
          name: 'Log uit',
          link: {
            name: 'login'
          }
        }
      }
    },
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