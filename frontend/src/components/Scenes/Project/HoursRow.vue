<template>
  <tr>
    <td>
      <participant :participant="registry.participant" small/>
    </td>
    <td>
      {{ this.formattedTimeSpent }}
    </td>
    <td>
      &euro;{{ this.costs }}
    </td>
    <td>
      {{ this.formatDate }}
    </td>
    <td class="status">
      <hour-registry-status :status="registry.status" @updateRegistryStatus="updateRegistryStatus"/>
    </td>
  </tr>
</template>

<script>
import Participant from "./Participant.vue";
import HourRegistryStatus from "./HourRegistryStatus.vue";

export default {
  name: "hoursRow",
  components: {HourRegistryStatus, Participant},
  inject: ['fetchService'],
  emits: ['updateStatus'],

  props: {
    registry: {
      type: Object,
      required: true,
    },
  },

  computed: {
    formattedTimeSpent() {
      return this.formatTimeSpent(this.calculateTimeSpent(this.registry));
    },
    costs() {
      const time = this.calculateTimeSpent(this.registry)[2];
      return Math.round(time * this.registry.participant.hourlyRate * 100) / 100;
    },
    formatDate() {
      const today = new Date();
      const date = new Date(this.registry.to);

      if (date.getDate() === today.getDate() &&
          date.getMonth() === today.getMonth() &&
          date.getFullYear() === today.getFullYear()) {
        return "Vandaag";
      }
      else {
        const days = ["Zo", "Ma", "Di", "Wo", "Do", "Vr", "Za"];
        const months = ["Jan", "Feb", "Mrt", "Apr", "Mei", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"];
        const sameYear = date.getFullYear() === today.getFullYear();

        return days[date.getDay()] + ". " + date.getDate() + " " + months[date.getMonth()] + (sameYear ? "" : ", " + date.getFullYear());
      }
    }
  },

  methods: {
    calculateTimeSpent() {
      let start = new Date(this.registry.from);
      let end = new Date(this.registry.to);

      // calculate time difference in hours and minutes
      let diff = end - start;
      let hours = Math.floor(diff / 1000 / 60 / 60);
      let minutes = Math.floor(diff / 1000 / 60) - (hours * 60);

      return [hours, minutes, hours + (minutes / 60)];
    },
    formatTimeSpent(timeSpent = [0, 0, 0], short = false) {
      if (short) return (timeSpent[2]).toFixed(2) + "h";
      return `${timeSpent[0]}h ${timeSpent[1]}m`;
    },
    async updateRegistryStatus(accepted) {
      const endpoint = accepted ? 'accept' : 'reject';

      const response = await this.fetchService.fetchUrl(`/hour-registrations/${this.registry.id}/${endpoint}`, 'POST');
      this.registry.status = response.status;

      this.$emit('updateStatus', this.registry);
    }
  }
}
</script>

<style scoped>
td {
  padding: 4px 8px;
  word-break: keep-all;
  white-space: nowrap;
  width: max-content;
  width: -moz-max-content;
}
</style>