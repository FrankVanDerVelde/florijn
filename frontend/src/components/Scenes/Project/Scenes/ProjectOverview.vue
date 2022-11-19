<template>
  <ProjectParticipantList :edit-button="true" :participants="project.participants" :client="project.client"/>

  <section class="pt-[48px]">
    <h2 class="header-2">Uren</h2>
    <div class="grid grid-cols-12 gap-4">
      <SummaryBlock label="Totaal gemaakte uren" :value="totalHours"/>
      <SummaryBlock label="Gemaakte uren deze maand" :value="hoursThisMonth"/>
      <SummaryBlock label="Gemaakte uren deze week" :value="hoursThisWeek"/>
      <SummaryBlock label="Ontwikkelkosten" :value="totalCosts"/>
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
        <HoursRow v-for="registry in hourRegistry" :key="registry.id" :registry="registry"/>
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
    this.hourRegistry = await this.fetchService.fetchJson(`/projects/${this.project.id}/hour-registrations`);
    console.log(this.hourRegistry);
  },

  props: {
    project: {
      type: Object,
      required: true
    }
  },

  computed: {
    totalHours() {
      return 24.5;
    },
    hoursThisMonth() {
      return 16.75;
    },
    hoursThisWeek() {
      return 8.25;
    },
    totalCosts() {
      return "€" + 2058.80;
    }
  },

  data() {
    return {
      hourRegistry: []
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