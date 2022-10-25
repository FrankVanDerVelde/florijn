<template>
  <div>
    <div class="font-bold text-[32px] mb-3">Uren Registratie</div>
    <div class="flex justify-center">
      <div class="flex flex-col gap-[11px] w-2/3 justify-center items-center">
        <p>{{year}}</p>
        <div class="flex justify-center gap-[11px]">
          <div @click="handlePrevWeekCLicked">prev</div>
          <div v-for="day in week">
            <!--          <CalendarDayOption :day-name="'test'" :date="'test'" :is-selected="false" @date-clicked="handleDateClicked"></CalendarDayOption>-->
            <div @click="handleDateClicked(day)"
                 :class="isSelectedForDay(day) ? 'selected-day-container' : 'unselected-day-container' ">
              <p class="font-medium" :class="!isSelectedForDay(day) ? 'text-neutral-800' : 'text-neutral-0'">
                {{ day.day }}</p>
              <p :class="!isSelectedForDay(day) ? 'text-neutral-600' : 'text-neutral-0'">{{ day.date }}</p>
            </div>
          </div>
          <div @click="handleNextWeekClicked">next</div>
        </div>
        <div class="w-full h-[31px] bg-primary-50 rounded-[9px] text-primary-500 font-semibold flex justify-center items-cente cursor-pointer">+ Toevoegen</div>
        <div class="flex w-full flex-col gap-4 justify-center">
          <div v-for="hourRegistration in hourRegistrations" :key="hourRegistration.id" class="flex justify-center">
            <div class="bg-neutral-0 rounded-[10px] hour-registration-row-shadow border-l-[12px] border-neutral-100 border-l-primary-500 w-2/3">
              <div class="py-[13px] pl-[12px] flex flex-col ">
                <p class="font-medium text-neutral-800">{{ hourRegistration.project.name }}</p>
                <div class="flex items-center gap-2 text-neutral-800">
                  <font-awesome-icon icon="clock"/>
                  <p>{{ hourRegistration.formattedFromToTime() }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import PrimaryButton from "../../Common/PrimaryButton.vue";
import CalendarDayOption from "./elements/CalendarDayOption.vue";
export default {
  name: "HourRegistrationOverview",
  components: {PrimaryButton, CalendarDayOption},
  inject: ['hourRegistrationRepository', 'dateService'],
  data() {
    return {
      week: [],
      hourRegistrations: [],
      selectedDayName: null,
      weekNumber: 0,
      year: ""
    }
  },
  created() {
    this.loadHourRegistrationsList();
    this.weekNumber = this.dateService.currentWeekOfYear();
    this.loadWeekBar();
  },
  methods: {
    loadWeekBar() {
      this.week = this.dateService.isoWeekDays(this.weekNumber).map(day => {
        return {
          day: day.format("dddd"),
          date: day.format("DD MMM")
        }
      });
      this.year = this.dateService.weekOfYear(this.weekNumber).format('YYYY');
    },
    loadHourRegistrationsList() {
      this.hourRegistrations = this.hourRegistrationRepository.fetchAllFor(0);
      console.log(this.hourRegistrations);
    },

    handleDateClicked(day) {
      this.selectedDayName = day.day;
    },

    isSelectedForDay(day) {
      return this.selectedDayName === day.day;
    },
    handleNextWeekClicked() {
        this.weekNumber += 1;
        this.loadWeekBar();
    },
    handlePrevWeekCLicked() {
      this.weekNumber -= 1;
      this.loadWeekBar();
    },
  }
}
</script>

<style scoped>

.day-container-item {
  @apply flex flex-col items-center w-[121px]  rounded-[6px] transition-colors ease-in-out cursor-pointer;
}

.selected-day-container {
  @apply day-container-item bg-primary-500 border-none;
}

.unselected-day-container {
  @apply day-container-item bg-neutral-50 border-[1px] border-neutral-100;
}

.hour-registration-row-shadow {
  box-shadow: 0 4px 25px 2px rgba(0, 0, 0, 0.08);
}

</style>