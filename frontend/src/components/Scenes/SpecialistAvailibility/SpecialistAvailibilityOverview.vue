<template>
  <div>

    <div v-if="this.showingModel" @click="" class="top-0 bottom-0 right-0 left-0 absolute">
      <div class="z-90 w-full h-full bg-neutral-900 opacity-20 absolute"></div>
      <div class="z-60 w-full h-full absolute flex justify-center items-center">
        <NewAvailabilityPopup
            :dayIndex="addingAvailabilityForDayIndex"
            :week-index="weekNumber"
            :availability="selectedAvailability"
            @availability-changed="handleAvailabilityChanged"
            @activity-cancel-clicked="showingModel = false"
        />
      </div>
    </div>

    <div class="font-bold text-neutral-900 text-[32px] mb-0">Beschikbaarheid</div>
    <div class="flex">
      <div class="flex flex-col gap-[11px]">
        <div class="flex justify-between w-full">
          <p class="text-3xl text-neutral-400 font-medium ">{{ year }}</p>
          <div class="flex gap-3">
            <button @click="handleCopyToNextWeek" class="secondary-button">
              <font-awesome-icon icon="fa-solid fa-paste" class="pr-2" />
              Kopieer naar volgende week
            </button>
            <button @click="handleThisWeekClicked" class="primary-button">Deze week</button>
          </div>
        </div>

        <div class="flex justify-center gap-[11px] pt-2">
          <div
              @click="handlePrevWeekCLicked"
              class="border-[1px] border-neutral-100 bg-neutral-50 rounded w-[50px] h-[50px] cursor-pointer flex justify-center items-center hover:bg-neutral-100"
          >
            <font-awesome-icon class="text-primary-500" icon="fa-solid fa-chevron-left"/>
          </div>

          <div v-for="day in week">
            <div class="flex flex-col">
              <div class="unselected-day-container">
                <p class="font-medium text-neutral-800">
                  {{ day.day }}</p>
                <p class="text-neutral-600">{{ day.date }}</p>
              </div>
              <div class="flex flex-col gap-3 pt-4">
                <div v-for="availability in this.availability.getAvailabilities(day.weekDayIndex)">
                  <AvailabilitySlot :availability="availability"  @click="handleAvailabilityClicked(availability)"/>
                </div>

                <div v-if="this.availability.getAvailabilities(day.weekDayIndex).length === 0">
                  <NotAvailableRow/>
                </div>

                <div
                    @click="handleAddAvailabilityClicked(day.weekDayIndex)"
                    class="w-full h-[31px] bg-primary-50 rounded-[9px] text-primary-500 font-semibold flex justify-center items-center cursor-pointer hover:bg-primary-100"
                >+ Toevoegen
                </div>
              </div>
            </div>
          </div>

          <div
              @click="handleNextWeekClicked"
              class="border-[1px] border-neutral-100 bg-neutral-50 rounded w-[50px] h-[50px] cursor-pointer flex justify-center items-center hover:bg-neutral-100"
          >
            <font-awesome-icon class="text-primary-500" icon="fa-solid fa-chevron-right"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PrimaryButton from "../../Common/PrimaryButton.vue";
import CalendarDayOption from "../SpecialistHourRegistration/elements/CalendarDayOption.vue";
import AvailabilitySlot from "./elements/AvailabilitySlot.vue";
import NewAvailabilityPopup from "./elements/NewAvailabilityPopup.vue";
import {WeekAvailability} from "../../models/WeekAvailability.js";
import NotAvailableRow from "./elements/NotAvailableRow.vue";

export default {
  name: "SpecialistAvailibilityOverview",
  components: {PrimaryButton, CalendarDayOption, AvailabilitySlot, NewAvailabilityPopup, NotAvailableRow},
  inject: ['dateService', 'availabilityRepository'],
  data() {
    return {
      week: [],
      availability: new WeekAvailability(),
      selectedAvailability: null,
      addingAvailabilityForDayIndex: null,
      weekNumber: 0,
      weekDelta: 0,
      year: "",
      showingModel: false,
      userId: Number(localStorage.id)
    }
  },
  async created() {
    console.log(`User id: ${this.userId}`)
    this.weekNumber = this.dateService.currentWeekOfYear();
    this.loadWeekBar();
    await this.loadWeekAvailability();
  },
  methods: {
    loadWeekBar() {
      this.week = this.dateService.isoWorkWeekDays(this.weekNumber)
          .map(day => {
            return {
              day: day.date.format("dddd"),
              date: day.date.format("DD MMM"),
              weekDayIndex: day.weekDayIndex,
            }
          });
      this.year = this.dateService.weekOfYear(this.weekNumber).format('YYYY');
    },

    async loadWeekAvailability() {
      const weekAvailability = await this.fetchWeekAvailability();
      this.sortAvailabilityPerDay(weekAvailability);
      console.log(this.availability);
    },

    async fetchWeekAvailability() {
      console.log(this.weekDelta);
      return await this.availabilityRepository.fetchAvailabilityForUserInWeek(this.userId, this.weekDelta);
    },

    sortAvailabilityPerDay(weekAvailability) {
      this.availability.clear();
      weekAvailability.forEach(availability => {
        let dayIndex = this.dateService.dayIndex(availability.date);
        this.availability.add(dayIndex, availability);
      });
    },

    handleThisWeekClicked() {
      this.selectThisWeek();
    },

    async selectThisWeek() {
      this.weekNumber = this.dateService.currentWeekOfYear();
      this.weekDelta = 0;
      this.loadWeekBar();
      await this.loadWeekAvailability();
    },

    async handleNextWeekClicked() {
      await this.loadWeekAvailabilityDelta(+1);
    },

    async handlePrevWeekCLicked() {
      await this.loadWeekAvailabilityDelta(-1);
    },

    async loadWeekAvailabilityDelta(delta) {
      this.weekNumber += delta;
      this.weekDelta += delta;
      this.loadWeekBar();
      await this.loadWeekAvailability();
    },

    handleAddActivityClicked() {
      this.showingModel = true
    },

    handleModelBackgroundClicked() {
      this.showingModel = false
    },

    async handleAddAvailabilityClicked(dayIndex) {
      console.log(`handleAddAvailabilityClicked: ${dayIndex}`);
      this.addingAvailabilityForDayIndex = dayIndex;
      this.showingModel = true;
    },

    handleAvailabilityClicked(availability) {
      this.selectedAvailability = availability;
      this.showingModel = true;
    },

    async handleAvailabilityChanged() {
      await this.loadWeekAvailability();
      this.showingModel = false;
    },

    async handleCopyToNextWeek() {
      try {
        let weekAvailability = await this.availabilityRepository.copyToWeek(this.userId, 1)
        this.weekNumber += 1;
        this.sortAvailabilityPerDay(weekAvailability);
        this.loadWeekBar()
      } catch (e) {
        console.log(e);
      }
    }
  }
}
</script>

<style scoped>

.day-container-item {
  @apply flex flex-col items-center w-[145px] rounded-[6px] transition-colors ease-in-out;
}

.unselected-day-container {
  @apply day-container-item bg-neutral-50 border-[1px] border-neutral-100;
}

.hour-registration-row-shadow {
  box-shadow: 0 4px 25px 2px rgba(0, 0, 0, 0.08);
}

</style>