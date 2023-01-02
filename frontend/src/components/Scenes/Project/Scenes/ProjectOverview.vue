<template>
  <ProjectParticipantList :edit-button="this.user.role === 'ADMIN'" :participants="project.participants"
                          :client="project.client"/>

  <section class="pt-[48px]">
    <h2 class="header-2">Uren</h2>
    <div class="grid grid-cols-12 gap-4">
      <SummaryBlock v-for="report in reports" :label="report.title" :value="report.value" :key="report.title"/>
    </div>

    <div class="overflow-x-auto mb-6">
      <HoursInfoPopup v-if="selectedHourRegistry != null"
                      :registry="selectedHourRegistry"
                      @close="selectedHourRegistry = null"
                      @changeStatus="updateRegistryStatus"/>
      <table class="w-full mt-4">
        <thead>
        <tr class="text-left">
          <th>Deelnemer</th>
          <th>Aantal uren</th>
          <th>Kosten</th>
          <th>Datum</th>
          <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <HoursRow v-for="registry in hourRegistry"
                  :key="registry.id"
                  :registry="registry"
                  @select="reg => selectedHourRegistry = reg"/>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script>
import SummaryBlock from "../SummaryBlock.vue";
import ProjectParticipantList from "../ProjectParticipantList.vue";
import HoursRow from "../HoursRow.vue";
import HoursInfoPopup from "../HoursInfoPopup.vue";

export default {
  name: "ProjectOverview",
  components: {HoursInfoPopup, HoursRow, ProjectParticipantList, SummaryBlock},
  inject: ['fetchService'],
  props: {
    project: {
      type: Object,
      required: true
    }
  },
  async beforeCreate(){
    if (localStorage.getItem("user") == null) {
      this.$router.push({name: "home"});
    }
  },
created(){
  if (localStorage.getItem("user") == null) {
    this.$router.push({name: "home"});
  }
},
  watch: {
    'project': async function () {
      await Promise.all([this.fetchReports(), this.fetchHourRegistry()])
    }
  },

  computed: {
    user() {
      return JSON.parse(localStorage.getItem('user'));
    },
    userId() {
      return Number.parseInt(this.user.id);
    }
  },

  data() {
    return {
      hourRegistry: [],
      reports: [],
      selectedHourRegistry: null
    }
  },

  methods: {
    async fetchReports() {
      this.reports = await this.fetchService.fetchJson(`/projects/${this.project.id}/reports?userId=${this.userId}`);
    },
    async fetchHourRegistry() {
      this.hourRegistry = await this.fetchService.fetchJson(`/projects/${this.project.id}/hour-registrations/users/${this.userId}`);
    },
    async updateRegistryStatus(accepted) {
      const endpoint = accepted ? 'accept' : 'reject';

      const response = await this.fetchService.fetchUrl(`/hour-registrations/${this.selectedHourRegistry.id}/${endpoint}`, 'POST');
      this.selectedHourRegistry.status = response.status;

      this.selectedHourRegistry = null;
      await this.fetchReports();
    }
  }
}
</script>

<style>
td, th {
  padding: 4px 8px;
  word-break: keep-all;
  white-space: nowrap;
  width: max-content;
  width: -moz-max-content;
}
</style>