<template>
  <div class="flex flex-row gap-4">
    <SummaryBox title="Aantal projecten" :value="projectCount"/>
    <SummaryBox title="Gewerkte uren" :value="dateService.formatTimeSpentDecimal(workedHours)"/>
    <SummaryBox title="Verdiensten" :value="formatMoney(earnings)"/>
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
      <QuickAction title="Uren registreren" :link="{name: 'hour-registration'}" icon="fa-clock"/>
      <QuickAction title="Beschikbaarheid aanpassen" :link="{name: 'availability'}" icon="fa-calendar-days"/>
      <QuickAction title="Skills wijzigen" :link="{name: 'profile-skills'}" icon="fa-lightbulb"/>
    </div>
  </HomeBox>
</template>

<script>
import SummaryBox from "../SummaryBox.vue";
import HomeBox from "../HomeBox.vue";
import QuickAction from "../QuickAction.vue";
import Asset from "../../../Common/Asset.vue";

export default {
  name: "SpecialistHomeLayout",
  components: {Asset, QuickAction, HomeBox, SummaryBox},
  inject: ['projectRepository', 'userFetchService', 'dateService'],

  async created() {
    Promise.all([
      this.loadProjects(),
      this.loadUserTotals()
    ])
  },

  data() {
    return {
      projects: [],
      workedHours: 0,
      earnings: 0,
      projectCount: 0,
    }
  },

  methods: {
    async loadProjects() {
      const [count, fetchedProjects] = await Promise.all([
        this.projectRepository.fetchTotalProjects(this.user.id),
        this.projectRepository.fetchProjects(this.user.id)
      ]);

      this.projectCount = count;
      this.projects = fetchedProjects;
    },
    async loadUserTotals() {
      const [hours, totalEarnings] = await Promise.all([
        this.projectRepository.fetchTotalWorkedHours(this.user.id),
        this.projectRepository.fetchEarnings(this.user.id)
      ]);

      this.workedHours = hours;
      this.earnings = totalEarnings;
    },
    formatMoney(string) {
      return new Intl.NumberFormat('nl-NL', { style: 'currency', currency: 'EUR' }).format(string);
    }
  },

  props: {
    user: {
      type: Object,
      required: true,
    }
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