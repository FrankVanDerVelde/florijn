<template>
  <tr @click="$emit('select', registry)">
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
      <hour-registry-status :status="registry.status"/>
    </td>
  </tr>
</template>

<script>
import Participant from "./Participant.vue";
import HourRegistryStatus from "./HourRegistryStatus.vue";

export default {
  name: "hoursRow",
  components: {HourRegistryStatus, Participant},
  inject: ['fetchService', 'dateService'],
  emits: ['updateStatus', 'select'],

  props: {
    registry: {
      type: Object,
      required: true,
    },
  },

  computed: {
    formattedTimeSpent() {
      return this.dateService.formatTimeSpent(this.dateService.calculateTimeSpent(this.registry.from, this.registry.to));
    },
    costs() {
      const time = this.dateService.calculateTimeSpent(this.registry.from, this.registry.to)[2];
      return Math.round(time * this.registry.participant.hourlyRate * 100) / 100;
    },
    formatDate() {
      const date = new Date(this.registry.to);
      return this.dateService.formatDateRelatively(date);
    }
  },
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

tr {
  transition: background-color 0.2s ease-in-out;
  cursor: pointer;
}
tr td:first-child {
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
}
tr td:last-child {
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
}

tr:hover {
  background-color: var(--neutral-75);
}
</style>