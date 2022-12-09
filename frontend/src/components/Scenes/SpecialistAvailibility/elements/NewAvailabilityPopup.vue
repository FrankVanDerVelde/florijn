<template>
  <div class="flex flex-col z-90 bg-neutral-0 py-[16px] rounded-[10px]">
    <p class="text-xl font-semibold mb-2 px-[16px]">Beschikbaarheid</p>
    <hr class="text-neutral-300">

    <form @submit.stop class="flex flex-col gap-4 pt-4 px-[16px]">
      <div class="form-row">
        <label class="font-semibold">Van</label>
        <input v-model="from" type="time" class="border border-neutral-300 p-2 rounded rounded-[4px]">
      </div>

      <div class="form-row">
        <label class="font-semibold">Tot</label>
        <input v-model="to" type="time" class="border border-neutral-300 p-2 rounded rounded-[4px]">
      </div>

      <div class="flex gap-2 justify-center">
        <button @click="$emit('activity-cancel-clicked')" class="secondary-button grow">Annuleren</button>
        <button @click="handleSaveTapped" class="primary-button grow">Opslaan</button>
        <button v-if="availability != null" @click="handleDeleteTapped" class="destructive-button grow">Verwijderen</button>
      </div>
    </form>
  </div>
</template>

<script>
import {Availability} from "../../../models/Availability.js";
import moment from "moment";

export default {
  name: "NewAvailabilityPopup",
  inject: ['projectFetchService', 'availabilityRepository', 'dateService'],
  emits: ['dismiss-clicked', 'activity-added', 'activity-cancel-clicked', 'availability-changed'],
  props: {
    dayIndex: Number,
    weekIndex: Number,
    availability: Availability
  },
  data() {
    return {
      projects: [],
      from: null,
      to: null,
      description: null,
      userId: Number(localStorage.getItem('id'))
    }
  },
  created() {
    if (this.availability) {
      this.from = moment(this.availability.from).format('HH:mm');
      this.to = moment(this.availability.to).format('HH:mm');
    }
  },

  methods: {

    async handleSaveTapped() {
      if (this.availability != null) {
        await this.updateAvailability();
      } else {
        await this.createNewAvailability();
      }
    },

    async createNewAvailability() {
      try {
        const date = this.dateService.dayOfWeek(this.weekIndex, this.dayIndex).format('yyyy-MM-DD');
        const from = moment(this.from, 'hh:mm').format('HH:mm');
        const to = moment(this.to, 'hh:mm').format('HH:mm');
        let result = await this.availabilityRepository.createAvailability(
            this.userId,
            date,
            from,
            to
        );
        console.log(result);

        this.notifyAvailabilityChanged();
      } catch (e) {
        console.error(e);
      }
    },

    async updateAvailability() {
      console.log('updateAvailability');
      try {
        const date = this.dateService.dayOfWeek(this.weekIndex, this.dayIndex)
        const formattedDate = moment(date).format('yyyy-MM-DD');
        const from = moment(this.from, 'HH:mm').format('HH:mm');
        const to = moment(this.to, 'HH:mm').format('HH:mm');
        console.log([date, from, to]);
        await this.availabilityRepository.updateAvailability(this.userId, formattedDate, from, to);

        this.notifyAvailabilityChanged();
      } catch (e) {
        console.error(e);
      }
    },

    convertTimeToDateString(timeStringInHoursMinutes) {
      return moment(timeStringInHoursMinutes, 'hh:mm').format('yyyy-MM-DD HH:mm');
    },

    async handleDeleteTapped() {
      try {
        await this.availabilityRepository.deleteAvailability(this.availability.id);
        this.notifyAvailabilityChanged()
      } catch (e) {
        console.error(e);
      }
    },

    notifyAvailabilityChanged() {
      this.$emit('availability-changed');
    },
  }
}
</script>

<style scoped>
.form-row {
  @apply flex flex-col gap-1;
}
</style>