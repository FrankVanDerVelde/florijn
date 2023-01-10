<template>
  <HomeLayout :summaries="summaries"
              :projects="projects"
              :project-count="projectCount"
              :quick-actions="quickActions"
              :hour-registrations="hourRegistrations"
              hour-registrations-cost-label="Verdiensten"
  />
</template>

<script>
import HomeLayout from "./HomeLayout.vue";

export default {
  name: "SpecialistHomeLayout",
  components: {HomeLayout},
  inject: ['projectRepository', 'userFetchService', 'dateService', 'hourRegistrationRepository'],

  async created() {
    await Promise.all([
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

  computed: {
    summaries() {
      return [{
        title: 'Aantal projecten',
        value: this.projectCount
      }, {
        title: 'Gewerkte uren',
        value: this.dateService.formatTimeSpentDecimal(this.workedHours)
      }, {
        title: 'Verdiensten',
        value: this.formatMoney(this.earnings)
      }];
    },
    quickActions() {
      return [{
        title: 'Uren registreren',
        link: {name: 'hour-registration'},
        icon: 'fa-clock'
      }, {
        title: 'Beschikbaarheid aanpassen',
        link: {name: 'availability'},
        icon: 'fa-calendar-days'
      }, {
        title: 'Skills wijzigen',
        link: {name: 'profile-skills'},
        icon: 'fa-lightbulb'
      }];
    }
  },

  methods: {
    async loadProjects() {
      const [count, fetchedProjects] = await Promise.all([
        this.projectRepository.fetchTotalProjects(),
        this.projectRepository.fetchProjects("UNARCHIVED")
      ]);

      this.projectCount = count;
      this.projects = fetchedProjects;
    },
    async loadUserTotals() {
      const [hours, totalEarnings] = await Promise.all([
        this.projectRepository.fetchTotalWorkedHours(),
        this.projectRepository.fetchEarnings()
      ]);

      this.workedHours = hours;
      this.earnings = totalEarnings;
    },
    async loadHourRegistrations() {
      this.hourRegistrations = await this.hourRegistrationRepository.fetchAllFor(this.user.id);
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

</style>