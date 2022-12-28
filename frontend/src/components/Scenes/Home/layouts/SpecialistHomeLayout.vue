<template>
  <div class="flex flex-row gap-4">
    <SummaryBox title="Aantal projecten" :value="projectCount"/>
    <SummaryBox title="Gewerkte uren" :value="dateService.formatTimeSpentDecimal(workedHours)"/>
    <SummaryBox title="Verdiensten" :value="formatMoney(earnings)"/>
  </div>

  <HomeBox>
    <h2 class="text-xl font-semibold mb-2">Jouw projecten</h2>

    <div v-if="projectCount === 0" class="flex gap-2 items-center text-app_red-500">
      <font-awesome-icon icon="fa-face-grin-squint" size="xl"></font-awesome-icon>
      <p>Je hebt nog geen actieve projecten.</p>
    </div>
    <router-link v-for="project in projects" :to="{name: 'project', params: {projectId: project.id}}" class="project flex items-center gap-2"
                 :key="project.id">
      <Asset :src="project.logoSrc" class="rounded-md h-8 w-8 object-contain"/>
      <div class="flex flex-col ml-2">
        <h3 class="text-lg font-medium">{{ project.title }}</h3>
      </div>
    </router-link>
  </HomeBox>
  <HomeBox>
    <h2 class="text-xl font-semibold mb-2">Geregistreerde uren</h2>

    <div v-if="hourRegistrations.length === 0" class="flex gap-2 items-center text-app_red-500">
      <font-awesome-icon icon="fa-face-grin-squint" size="xl"></font-awesome-icon>
      <p>Je hebt nog geen uren geregistreerd.</p>
    </div>
    <table v-else class="w-full mt-4">
      <thead>
      <tr class="text-left">
        <th>Rol</th>
        <th>Aantal uren</th>
        <th>Kosten</th>
        <th>Datum</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody>
      <HoursRow v-for="registry in hourRegistrations"
                :show-project="true"
                :key="registry.id"
                :registry="registry"/>
      </tbody>
    </table>
  </HomeBox>


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
import HoursRow from "../../Project/HoursRow.vue";

export default {
  name: "SpecialistHomeLayout",
  components: {HoursRow, Asset, QuickAction, HomeBox, SummaryBox},
  inject: ['projectRepository', 'userFetchService', 'dateService', 'hourRegistrationRepository'],

  async created() {
    Promise.all([
      this.loadProjects(),
      this.loadUserTotals(),
      this.loadHourRegistrations()
    ])
  },

  data() {
    return {
      projects: [],
      workedHours: 0,
      earnings: 0,
      projectCount: 0,
      hourRegistrations: [],
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
    async loadHourRegistrations() {
      this.hourRegistrations = await this.hourRegistrationRepository.fetchAllFor(this.user.id);
      console.log(this.hourRegistrations)
    },
    formatMoney(string) {
      return new Intl.NumberFormat('nl-NL', {style: 'currency', currency: 'EUR'}).format(string);
    },
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