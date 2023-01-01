<template>
  <HomeLayout :quick-actions="quickActions"
              :project-count="projectCount"
              :projects="projects"
              :summaries="summaries"
              projects-box-error-message="Er zijn nog geen projecten."
  />
</template>

<script>
import HomeLayout from "./HomeLayout.vue";

export default {
  name: "AdminHomeLayout",
  components: {HomeLayout},
  inject: ['projectRepository', 'userFetchService'],

  async created() {
    await Promise.all([
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

  computed: {
    summaries() {
      return [{
        title: 'Aantal klanten',
        value: this.clientCount
      }, {
        title: 'Aantal leden',
        value: this.memberCount
      }, {
        title: 'Aantal projecten',
        value: this.projectCount
      }];
    },
    quickActions() {
      return [{ // TODO: Add links for adding clients and specialists.
        title: 'Klant toevoegen',
        link: '',
        icon: 'fa-building'
      }, {
        title: 'Specialist toevoegen',
        link: '',
        icon: 'fa-user-plus'
      }, {
        title: 'Project aanmaken',
        link: {name: 'new-project'},
        icon: 'fa-bars-progress'
      }]
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