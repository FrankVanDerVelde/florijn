<template>
  <div class="bg-neutral-0 rounded-[4px] border-[1px] border-neutral-100 hour-registration-row-shadow  border-neutral-100 w-full cursor-pointer hover:bg-neutral-100">
    <div class="py-[13px] px-[12px] flex justify-between">
      <div class="flex flex-col justify-center w-full">
        <div class="flex flex-col text-center gap-2 text-neutral-800">
          <p class="font-semibold text-[14px]">{{ `${from} - ${to}` }}</p>
          <p class="right-0 text-[13px]">{{ differance }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Availability} from "../../../models/Availability.js";
import moment from "moment";

export default {
  name: "AvailabilitySlot",
  props: {
    availability: Availability
  },
  data() {
    return {
      from: null,
      to: null,
      differance: null
    }
  },
  created() {
    if (this.availability) {
      this.formatTime();
      this.calculateTimeDifferance()
    }
  },
  methods: {
    formatTime() {
      this.from = moment(this.availability.from).format('HH:mm');
      this.to = moment(this.availability.to).format('HH:mm');
    },

    calculateTimeDifferance() {
      const from = moment(this.availability.from);
      const to = moment(this.availability.to);

      const minutesDifferance = Math.abs(from.diff(to, 'seconds'));
      this.differance = moment()
          .startOf('day')
          .add(minutesDifferance, 'seconds')
          .format('H[h] mm[m]');
    }
  }
}
</script>

<style scoped>

</style>