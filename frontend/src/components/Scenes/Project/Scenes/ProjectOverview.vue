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
      hourRegistry: [
        {
          id: 0,
          participant: {
            user: {
              id: 1,
              firstName: "Withney",
              lastName: "Keulen",
              avatarUrl: "/src/assets/avatars/avatar2.avif",
              email: "withneyk@florijn.com"
            },
            role: "Lead Developer",
          },
          startTime: "2022-10-14T12:00:00.000Z",
          endTime: "2022-10-14T14:45:00.000Z",
          hourlyRate: 60,
          status: 'AWAITING'
        },
        {
          id: 1,
          participant: {
            user: {
              id: 1,
              firstName: "Withney",
              lastName: "Keulen",
              avatarUrl: "/src/assets/avatars/avatar2.avif",
              email: "withneyk@florijn.com",
            },
            role: "Lead Developer"
          },
          startTime: "2022-10-13T09:00:00.000Z",
          endTime: "2022-10-13T11:30:00.000Z",
          hourlyRate: 60,
          status: 'APPROVED'
        },
        {
          id: 2,
          participant: {
            user: {
              id: 2,
              firstName: "Jan",
              lastName: "Timmermans",
              avatarUrl: "/src/assets/avatars/avatar3.avif",
              email: "jant@florijn.com"
            },
            role: "Designer",
          },
          startTime: "2022-10-11T14:00:00.000Z",
          endTime: "2022-10-11T18:20:00.000Z",
          hourlyRate: 40,
          status: 'APPROVED'
        },
        {
          id: 3,
          participant: {
            user: {
              id: 1,
              firstName: "Withney",
              lastName: "Keulen",
              avatarUrl: "/src/assets/avatars/avatar2.avif",
              email: "withneyk@florijn.com"
            },
            role: "Lead Developer",
          },
          startTime: "2021-03-08T15:30:00.000Z",
          endTime: "2021-03-08T19:20:00.000Z",
          hourlyRate: 60,
          status: 'DECLINED'
        }
      ]
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