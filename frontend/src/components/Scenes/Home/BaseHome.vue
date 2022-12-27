<template>
  <div class="page-main-mw p-10">
    <div class="head flex flex-row items-center gap-4 text-primary-500">
      <Asset :src="user.avatarUrl" class="w-20 rounded-full"/>
      <div>
        <p>Hi {{ name }}</p>
        <h1 class="text-4xl font-semibold">Welkom terug! 👋</h1>
      </div>
    </div>

    <div class="flex flex-col gap-2 mt-6">
      <h2 class="text-xl font-semibold">Jouw projecten</h2>

      <div class="flex flex-row">
        <div v-for="project in projects" class="flex rounded-md bg-gray-200 p-2" :key="project.id">
          <Asset :src="project.image" class="w-20 rounded-md"/>
          <div class="flex flex-col gap-2 ml-2">
            <h3 class="text-lg font-semibold">{{ project.name }}</h3>
            <p>{{ project.description }}</p>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import Asset from "../../Common/Asset.vue";

export default {
  name: "BaseHome",
  components: {Asset},
  inject: ['storedTokenRepository', 'projectRepository'],

  created() {
    console.log(this.user)
    if (this.user == null) {
      this.$router.push({name: "login"});
      return;
    }

    this.loadProjects();
  },

  data() {
    return {
      projects: []
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

  methods: {
    async loadProjects() {
      this.projects = await this.projectRepository.fetchProjects("UNARCHIVED");
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Unbounded:wght@300;400;500;600&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap');

h2 {
  font-family: 'Poppins', sans-serif;
}

.head {
  font-family: 'Unbounded', cursive;
}

</style>