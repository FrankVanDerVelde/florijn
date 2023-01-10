<template>
  <div>

    <div v-if="this.showingModel" class="top-0 bottom-0 right-0 left-0 absolute">
      <div class="z-90 w-full h-full bg-neutral-900 opacity-20 absolute"></div>
      <div class="z-60 w-full h-full absolute flex justify-center items-center">
        <NewAvailabilityPopup
            :year="selected.year"
            :day-of-year="addingAvailabilityForDayOfYear"
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
          <p class="text-3xl text-neutral-400 font-medium ">{{ selected.year }}</p>
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
                <div v-for="availability in this.availability.getAvailabilities(day.weekDayIndex)" :key="availability.id">
                  <AvailabilitySlot :availability="availability"  @click="handleAvailabilityClicked(availability, day.weekDayIndex)"/>
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
import moment from "moment";

export default {
  name: "SpecialistAvailibilityOverview",
  components: {PrimaryButton, CalendarDayOption, AvailabilitySlot, NewAvailabilityPopup, NotAvailableRow},
  inject: ['dateService', 'availabilityRepository', 'storedTokenRepository'],
  data() {
    return {
      week: [],
      availability: new WeekAvailability(),
      selectedAvailability: null,
      addingAvailabilityForDayOfYear: null,
      selected: {
        year: 0,
        week: 0,
        dayOfYear: 0,
      },
      showingModel: false,
      userId: null
    }
  },
  async created() {
    console.dir(this.storedTokenRepository);
    console.dir(this.storedTokenRepository.getUser());
    this.userId = this.storedTokenRepository.getUser().id;
    this.selectThisWeek();
    await this.loadWeekAvailability();
  },
  methods: {

    selectThisWeek() {
      this.selected.week = this.dateService.currentWeekOfYear();
      this.selected.year = this.dateService.currentYear();
      this.selected.dayOfYear = this.dateService.currentDayOfYear();
      this.loadWeekBar();
    },

    loadWeekBar() {
      this.week = this.dateService.isoWorkWeekDays(this.selected.week)
          .map(day => {
            return {
              day: day.date.format("dddd"),
              date: day.date.format("DD MMM"),
              weekDayIndex: day.weekDayIndex,
            }
          });
    },

    async loadWeekAvailability() {
      const weekAvailability = await this.fetchWeekAvailability();
      this.sortAvailabilityPerDay(weekAvailability);
    },

    async fetchWeekAvailability() {
      return await this.availabilityRepository.fetchAvailabilityForUserInWeek(
          this.userId,
          this.selected.week,
          this.selected.year
      );
    },

    sortAvailabilityPerDay(weekAvailability) {
      this.availability.clear();
      weekAvailability.forEach(availability => {
        let dayIndex = this.dateService.dayIndex(availability.date);
        this.availability.add(dayIndex, availability);
      });
      console.dir(this.availability)
    },

    async handleThisWeekClicked() {
      this.selectThisWeek();
      await this.loadWeekAvailability();
    },

    async handleNextWeekClicked() {
      await this.loadWeekAvailabilityDelta(+1);
    },

    async handlePrevWeekCLicked() {
      await this.loadWeekAvailabilityDelta(-1);
    },

    async loadWeekAvailabilityDelta(delta) {
      this.adjustSelectedWeekByDelta(delta);
      this.loadWeekBar();
      await this.loadWeekAvailability();
    },

    adjustSelectedWeekByDelta(delta) {
      let newSelectedMomentDate = moment()
          .year(this.selected.year)
          .dayOfYear(this.selected.dayOfYear)

      // Adding -1 will result in a subtraction
      newSelectedMomentDate = delta > 0
          ? newSelectedMomentDate.add(delta, 'week')
          : newSelectedMomentDate.subtract(Math.abs(delta), 'week');

      this.selected.week = newSelectedMomentDate.isoWeek();
      this.selected.year = newSelectedMomentDate.year();
      this.selected.dayOfYear = newSelectedMomentDate.dayOfYear();
    },

    handleAddActivityClicked() {
      this.showingModel = true
    },

    handleModelBackgroundClicked() {
      this.showingModel = false
    },

    async handleAddAvailabilityClicked(dayIndex) {
      this.setAddingAvailabilityForDayOfYear(dayIndex);
      this.selectedAvailability = null;
      this.showingModel = true;
    },

    setAddingAvailabilityForDayOfYear(isoWeekday) {
      this.addingAvailabilityForDayOfYear = moment()
          .year(this.selected.year)
          .isoWeek(this.selected.week)
          .isoWeekday(isoWeekday)
          .dayOfYear();
    },

    handleAvailabilityClicked(availability, dayIndex) {
      this.setAddingAvailabilityForDayOfYear(dayIndex);
      this.selectedAvailability = availability;
      this.showingModel = true;
    },

    async handleAvailabilityChanged() {
      await this.loadWeekAvailability();
      this.showingModel = false;
    },

    async handleCopyToNextWeek() {
      try {
        const weekAvailability = await this.availabilityRepository.copyToWeek(
            this.userId,
            this.selected.week,
            this.selected.year
        );

        this.adjustSelectedWeekByDelta(+1);
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