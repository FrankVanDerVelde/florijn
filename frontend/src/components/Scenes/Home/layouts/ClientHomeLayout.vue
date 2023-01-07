<template>
  <HomeLayout :summaries="summaries"
              :projects="projects"
              :project-count="projectCount"
              :quick-actions="quickActions"
              :hour-registrations="hourRegistrations"
              hour-registrations-box-error-message="Er zijn nog geen uren geregistreerd voor jouw projecten."
  />
</template>

<script>
import HomeLayout from "./HomeLayout.vue";

export default {
  name: "ClientHomeLayout",
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
        title: 'Kosten',
        value: this.formatMoney(this.earnings)
      }];
    },
    quickActions() {
      return [{
        title: 'Projecten bekijken',
        link: {name: 'projects'},
        icon: 'fa-bars-progress'
      }, {
        title: 'Profiel aanpassen',
        link: {name: 'personal-info'},
        icon: 'fa-user'
      }];
    }
  },

  methods: {
    // TODO: Retrieve registered hours, total hours, and spendings from the backend for clients.
    async loadProjects() {
      const [count, fetchedProjects] = await Promise.all([
        this.projectRepository.fetchTotalProjects(this.user.id),
        this.projectRepository.fetchProjects(this.user.id, "UNARCHIVED")
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

</style>