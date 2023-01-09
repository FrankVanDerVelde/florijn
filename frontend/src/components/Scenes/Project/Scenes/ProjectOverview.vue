<template>
  <ProjectParticipantList :edit-button="this.user.role === 'ADMIN'" :participants="project.participants"
                          :client="project.client"/>

  <section class="pt-[48px]">
    <div class="flex items-center justify-between mb-4">
      <h2 class="header-2 !mb-0">Uren</h2>
      <router-link
          v-if="user?.role === 'SPECIALIST'"
          :to="{name: 'hour-registration'}"
          class="bg-primary-400 rounded-md bold p-2 h-[32px] flex items-center text-neutral-0">
        Uren registreren
      </router-link>
    </div>

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
        <tr v-if="hourRegistry.length === 0">
          <td colspan="5" class="text-red-500">Er zijn nog geen uren geregistreerd.</td>
        </tr>

        <HoursRow v-for="registry in hourRegistry"
                  :key="registry.id"
                  :registry="registry"
                  @updateStatus="fetchReports"/>
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
  inject: ['projectRepository'],

  props: {
    project: {
      type: Object,
      required: true
    }
  },
created(){
  if (this.user == null) {
    this.$router.push({name: "login"});
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
      return Number.parseInt(this.user?.id ?? "-1");
    }
  },

  data() {
    return {
      hourRegistry: [],
      reports: [],
    }
  },

  methods: {
    async fetchReports() {
      this.reports = await this.projectRepository.fetchProjectReports(this.project.id);
    },
    async fetchHourRegistry() {
      this.hourRegistry = await this.projectRepository.fetchProjectHourRegistrationsForUser(this.project.id);
    },
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