<template>
  <div class="page-main-mw p-3 md:p-10 flex flex-col gap-4">
    <div class="head flex flex-row items-center gap-4 text-primary-500">
      <Asset :src="user.avatarUrl" class="w-20 rounded-full"/>
      <div>
        <p>Hi {{ name }}</p>
        <h1 class="text-4xl font-semibold">Welkom terug! 👋</h1>
      </div>
    </div>

    <AdminHomeLayout v-if="user.role === 'ADMIN'"/>
    <SpecialistHomeLayout v-else-if="user.role === 'SPECIALIST'" :user="user"/>
    <ClientHomeLayout v-else-if="user.role === 'CLIENT'" :user="user"/>
  </div>
</template>

<script>
import Asset from "../../Common/Asset.vue";
import AdminHomeLayout from "./layouts/AdminHomeLayout.vue";
import SpecialistHomeLayout from "./layouts/SpecialistHomeLayout.vue";
import ClientHomeLayout from "./layouts/ClientHomeLayout.vue";

export default {
  name: "BaseHome",
  components: {ClientHomeLayout, SpecialistHomeLayout, AdminHomeLayout, Asset},
  inject: ['storedTokenRepository', 'projectRepository'],

  created() {
    if (this.user == null) {
      this.$router.push({name: "login"});
      return;
    }
  },

  computed: {
    name() {
      if (this.user.role === "CLIENT") {
        return this.user.name;
      }
      return this.user.firstName;
    },
    user() {
      return JSON.parse(localStorage.getItem("user"));
    }
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;500;600&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');

.head {
  font-family: 'Unbounded', cursive;
}

</style>