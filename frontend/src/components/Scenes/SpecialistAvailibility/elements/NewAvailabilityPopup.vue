<template>
  <div class="flex flex-col z-90 bg-neutral-0 py-[16px] rounded-[10px] w-[320px]">
    <div class="flex justify-between mb-2 px-[16px]">
      <div class="flex flex-col">
        <p class="text-xl font-semibold ">Beschikbaarheid</p>
        <p class="text-neutral-600 font-medium">{{this.momentDate.format('LL')}}</p>
      </div>
      <font-awesome-icon
          v-if="availability != null && editingEnabled"
          @click="handleDeleteTapped"
          class="text-app_red-500 text-xl cursor-pointer hover:text-app_red-600"
          icon="fa-solid fa-trash-can"
      />

      <font-awesome-icon
          v-if="!editingEnabled"
          @click="$emit('activity-cancel-clicked')"
          class="text-xl cursor-pointer"
          icon="fa-solid fa-xmark"
      />

    </div>

    <hr class="text-neutral-300">

    <div v-if="editingEnabled" class="pt-4 px-[16px]">
      <form @submit.stop class="flex flex-col gap-4 ">

        <div class="bg-app_indigo-50 p-2 rounded" v-if="holidayName">
          <p class="font-bold text-app_indigo-700">Vrije dag</p>
          <p class="text-app_indigo-700">deze dag is het {{this.holidayName}}</p>
        </div>

        <div class="form-row">
          <label class="font-semibold">Van</label>
          <input v-model="from" type="time" class="border border-neutral-300 p-2 rounded rounded-[4px]">
        </div>

        <div class="form-row">
          <label class="font-semibold">Tot</label>
          <input v-model="to" type="time" class="border border-neutral-300 p-2 rounded rounded-[4px]">
        </div>

        <div class="flex gap-2 justify-center">
          <button
              @click="$emit('activity-cancel-clicked')"
              class="secondary-button grow"
          >Annuleren</button>

          <button
              :disabled="from === null || to === null"
              @click="handleSaveTapped"
              :class="isSaveButtonEnabled() ? 'primary-button' : 'primary-button-disabled'"
              class="grow"
          >Opslaan</button>
        </div>
      </form>
      <p v-if="didEncounterError" class="text-app_red-500 pt-2">Er is iets mis gegaan bij het opslaan van je verzoek, probeer het opnieuw.</p>
    </div>

    <div v-if="!editingEnabled" class="pt-4 px-[16px]">
      <div v-if="availability" class="flex flex-col gap-2">
        <div class="form-row">
          <label class="font-semibold">Tot</label>
          <p>{{from}}</p>
        </div>

        <div class="form-row">
          <label class="font-semibold">Tot</label>
          <p>{{to}}</p>
        </div>
        <p class="text-neutral-600"> Je kan je beschikbaarheid niet aanpassen die in het verleden is, of op de dag van de beschikbaarheid. Zorg ervoor dat je de dag van tevoren je beschikbaarheid instelt.</p>
      </div>
      <p v-else>Je kan geen nieuwe beschikbaarheid aanmaken voor een dag die al is geweest of op de dag zelf. Zorg ervoor dat je de dag van tevoren je beschikbaarheid instelt.</p>
    </div>
  </div>
</template>

<script>
import {Availability} from "../../../models/Availability.js";
import moment from "moment";

export default {
  name: "NewAvailabilityPopup",
  inject: ['projectFetchService', 'memoryAvailabilityRepository', 'dateService', 'holidays'],
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
      momentDate: null,
      holidayName: null,
      didEncounterError: false,
      editingEnabled: true,
      userId: Number(localStorage.getItem('id'))
    }
  },
  created() {
    this.momentDate = this.dateService.dayOfWeek(this.weekIndex, this.dayIndex).startOf('day');
    if (this.availability) {
      this.from = moment(this.availability.from).format('HH:mm');
      this.to = moment(this.availability.to).format('HH:mm');
    }
    this.loadHolidayEvent();

    if (this.momentDate.isSameOrBefore(moment())) {
      this.editingEnabled = false;
    }
  },

  methods: {

    loadHolidayEvent() {
      const holidays = this.holidays.isHoliday(this.momentDate.toDate());
      this.holidayName = holidays[0]?.name;
    },

    async handleSaveTapped() {
      this.didEncounterError = false;
      try {
        if (this.availability != null) {
          await this.updateAvailability();
        } else {
          await this.createNewAvailability();
        }
      } catch (e) {
        this.didEncounterError = true;
      }
    },

    async createNewAvailability() {
      try {
        const date = this.momentDate.format('yyyy-MM-DD')
        const from = moment(this.from, 'hh:mm').format('HH:mm');
        const to = moment(this.to, 'hh:mm').format('HH:mm');
        let result = await this.memoryAvailabilityRepository.createAvailability(this.userId, date, from, to);
        console.log(result);
        this.notifyAvailabilityChanged();
      } catch (e) {
        console.error(e);
        throw e;
      }
    },

    async updateAvailability() {
      console.log('updateAvailability');
      try {
        console.log(this.availability);
        const date = moment(this.availability.date).format('yyyy-MM-DD');
        const from = moment(this.from, 'HH:mm').format('HH:mm');
        const to = moment(this.to, 'HH:mm').format('HH:mm');
        console.log([date, from, to]);
        await this.memoryAvailabilityRepository.updateAvailability(this.availability.id, date, from, to);
        this.notifyAvailabilityChanged();
      } catch (e) {
        console.error(e);
        throw e;
      }
    },

    convertTimeToDateString(timeStringInHoursMinutes) {
      return moment(timeStringInHoursMinutes, 'hh:mm').format('yyyy-MM-DD HH:mm');
    },

    async handleDeleteTapped() {
      try {
        await this.memoryAvailabilityRepository.deleteAvailability(this.availability.id);
        this.notifyAvailabilityChanged()
      } catch (e) {
        console.error(e);
      }
    },

    notifyAvailabilityChanged() {
      this.$emit('availability-changed');
    },

    isSaveButtonEnabled() {
      return this.from !== null && this.to !== null;
    }
  }
}
</script>

<style scoped>
.form-row {
  @apply flex flex-col gap-1;
}
</style>