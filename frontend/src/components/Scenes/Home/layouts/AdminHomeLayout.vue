<template>
  <div class="flex flex-row gap-4">
    <SummaryBox title="Aantal klanten" :value="clientCount"/>
    <SummaryBox title="Aantal leden" :value="memberCount"/>
    <SummaryBox title="Aantal projecten" :value="projectCount"/>
  </div>

  <div class="flex flex-col gap-2">
    <HomeBox>
      <h2 class="text-xl font-semibold mb-2">Jouw projecten</h2>

      <router-link v-for="project in projects" :to="{name: 'project', params: {projectId: project.id}}" class="project flex items-center gap-2"
                   :key="project.id">
        <Asset :src="project.logoSrc" class="rounded-md h-8 w-8 object-contain"/>
        <div class="flex flex-col ml-2">
          <h3 class="text-lg font-medium">{{ project.title }}</h3>
        </div>
      </router-link>
    </HomeBox>
  </div>

  <HomeBox class="w-full md:w-fit">
    <h2 class="text-xl font-semibold mb-2">Snelle acties</h2>
    <div class="flex flex-col md:flex-row gap-1 md:gap-5">
      <QuickAction title="Klant toevoegen" link="" icon="fa-building"/>
      <QuickAction title="Specialist toevoegen" link="" icon="fa-user-plus"/>
      <QuickAction title="Project aanmaken" :link="{name: 'new-project'}" icon="fa-bars-progress"/>
    </div>
  </HomeBox>
</template>

<script>
import SummaryBox from "../SummaryBox.vue";
import HomeBox from "../HomeBox.vue";
import QuickAction from "../QuickAction.vue";
import Asset from "../../../Common/Asset.vue";

export default {
  name: "AdminHomeLayout",
  components: {Asset, QuickAction, HomeBox, SummaryBox},
  inject: ['projectRepository', 'userFetchService'],

  async created() {
    Promise.all([
      this.loadProjects(),
      this.loadUserTotals()
    ])
  },

  data() {
    return {
      projects: [],
      memberCount: 0,
      clientCount: 0,
      projectCount: 0,
    }
  },

  methods: {
    async loadProjects() {
      const [count, fetchedProjects] = await Promise.all([
        this.projectRepository.fetchTotalProjects(),
        this.projectRepository.fetchProjects()
      ]);

      this.projectCount = count;
      this.projects = fetchedProjects;
    },
    async loadUserTotals() {
      const response = await this.userFetchService.fetchJson('/counts');
      for (const [key, value] of Object.entries(response)) {
        if (value.role === "1") {
          this.memberCount = value.count;
        } else if (value.role === "2") {
          this.clientCount = value.count;
        }
      }
    },
  }
}
</script>

<style scoped>

h2 {
  font-family: 'Poppins', sans-serif;
}

.project:not(:last-child) {
  border-bottom: 1px solid var(--gray-100);
  padding-bottom: 8px;
}

.project:not(:first-child) {
  padding-top: 8px;
}

</style>