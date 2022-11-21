<template>
  <ProjectParticipantList :edit-button="userId >= 2" :participants="project.participants" :client="project.client"/>

  <section class="pt-[48px]">
    <h2 class="header-2">Uren</h2>
    <div class="grid grid-cols-12 gap-4">
      <SummaryBlock v-for="report in reports" :label="report.title" :value="report.value" :key="report.title"/>
    </div>

    <div class="overflow-x-auto mb-6">
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
        <HoursRow v-for="registry in hourRegistry" :key="registry.id" :registry="registry" @updateStatus="fetchReports"/>
        </tbody>
      </table>
    </div>
  </section>
</template>

<script>
import SummaryBlock from "../SummaryBlock.vue";
import ProjectParticipantList from "../ProjectParticipantList.vue";
import HoursRow from "../HoursRow.vue";

export default {
  name: "ProjectOverview",
  components: {HoursRow, ProjectParticipantList, SummaryBlock},
  inject: ['fetchService'],

  async created() {
    await Promise.all([this.fetchReports(), this.fetchHourRegistry()]);
  },

  props: {
    project: {
      type: Object,
      required: true
    }
  },

  watch: {
    '$route.query.userId': async function () {
      await Promise.all([this.fetchReports(), this.fetchHourRegistry()]);
    }
  },

  computed: {
    userId() {
      return Number.parseInt(this.$route.query.userId ?? 2) // admin = 2, user = 1
    }
  },

  data() {
    return {
      hourRegistry: [],
      reports: []
    }
  },

  methods: {
    async fetchReports() {
      this.reports = await this.fetchService.fetchJsonPost(`/projects/${this.project.id}/reports`, {userId: this.userId});
    },
    async fetchHourRegistry() {
      this.hourRegistry = await this.fetchService.fetchJson(`/projects/${this.project.id}/hour-registrations/users/${this.userId}`);
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