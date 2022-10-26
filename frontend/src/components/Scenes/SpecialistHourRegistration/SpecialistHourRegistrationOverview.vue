<template>
  <div>
    <div v-if="this.showingModel" @click="handleModelBackgroundClicked" class="top-0 bottom-0 right-0 left-0 absolute">
      <div class="z-90 w-full h-full bg-neutral-900 opacity-20 absolute"></div>
      <div class="z-60 w-full h-full absolute flex justify-center items-center">
        <div class="z-90 bg-neutral-0 p-[16px] rounded-[10px]">
          <p class="text-xl font-medium">Nieuwe activiteit</p>
          <hr>
        </div>
      </div>
    </div>

    <div class="font-bold text-neutral-900 text-[32px] mb-0">Uren Registratie</div>

    <div class="flex justify-center">
      <div class="flex flex-col gap-[11px] justify-center">
        <p class="text-3xl text-neutral-400 font-medium ">{{year}}</p>
        <div class="flex justify-center gap-[11px] pt-2">
          <div
              @click="handlePrevWeekCLicked"
              class="border-[1px] border-neutral-100 bg-neutral-50 rounded w-[50px] h-[50px] cursor-pointer flex justify-center items-center hover:bg-neutral-100"
          >
            <font-awesome-icon class="text-primary-500" icon="fa-solid fa-chevron-left"/>
          </div>

          <div v-for="day in week">
            <div @click="handleDateClicked(day.weekDayIndex)"
                 :class="isSelectedForDay(day) ? 'selected-day-container' : 'unselected-day-container' ">
              <p class="font-medium" :class="!isSelectedForDay(day) ? 'text-neutral-800' : 'text-neutral-0'">
                {{ day.day }}</p>
              <p :class="!isSelectedForDay(day) ? 'text-neutral-600' : 'text-neutral-0'">{{ day.date }}</p>
            </div>
          </div>
          <div
              @click="handleNextWeekClicked"
              class="border-[1px] border-neutral-100 bg-neutral-50 rounded w-[50px] h-[50px] cursor-pointer flex justify-center items-center hover:bg-neutral-100"
          ><font-awesome-icon class="text-primary-500" icon="fa-solid fa-chevron-right"/></div>
        </div>

        <div @click="handleAddActivityClicked" class="w-full h-[31px] bg-primary-50 rounded-[9px] text-primary-500 font-semibold flex justify-center items-cente cursor-pointer">+ Toevoegen</div>

        <div class="flex w-full flex-col gap-4 justify-center">
          <div v-for="hourRegistration in filteredHourRegistrations" :key="hourRegistration.id" class="flex justify-center">
            <div class="bg-neutral-0 rounded-[10px] hour-registration-row-shadow border-l-[12px] border-neutral-100 border-l-primary-500 w-full">
              <div class="py-[13px] pl-[12px] flex flex-col ">
                <p class="font-medium text-neutral-800">{{ hourRegistration.project.name }}</p>
                <div class="flex items-center gap-2 text-neutral-800">
                  <font-awesome-icon icon="clock"/>
                  <p>{{ hourRegistration.formattedFromToTime() }}</p>
                </div>
              </div>
            </div>
          </div>
          <div v-if="filteredHourRegistrations.length === 0" class="flex flex-col justify-center items-center border-dashed border-2 border-neutral-200 p-5 rounded-2xl">
            <p class="font-semibold text-neutral-900">geen activiteit op deze dag</p>
            <p class="text-neutral-600">Klik op "Toevoegen" om een nieuwe activiteit toe te voegen.</p>
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
      filteredHourRegistrations: [],
      weekNumber: 0,
      selectedDayIndex: null,
      year: "",
      showingModel: false
    }
  },
  created() {
    this.loadHourRegistrationsList();
    this.weekNumber = this.dateService.currentWeekOfYear();
    this.loadWeekBar();

    this.selectedDayIndex = this.dateService.currentDayOfWeek();
    this.filterHourRegistrations();
  },
  methods: {
    loadWeekBar() {
      this.week = this.dateService.isoWeekDays(this.weekNumber).map(day => {
        return {
          day: day.date.format("dddd"),
          date: day.date.format("DD MMM"),
          weekDayIndex: day.weekDayIndex
        }
      });
      this.year = this.dateService.weekOfYear(this.weekNumber).format('YYYY');
    },

    loadHourRegistrationsList() {
      this.hourRegistrations = this.hourRegistrationRepository.fetchAllFor(0);
      console.log(this.hourRegistrations);
    },

    handleDateClicked(dayIndex) {
      this.selectedDayIndex = dayIndex;
      this.filterHourRegistrations();
    },

    filterHourRegistrations() {
      const weekDayDate = this.dateService.dayOfWeek(this.weekNumber, this.selectedDayIndex).toDate();
      this.filteredHourRegistrations = this.hourRegistrations.filter((hourReistration => {
        return this.dateService.isSameDay(hourReistration.from, weekDayDate)
      }));
    },

    isSelectedForDay(day) {
      return this.selectedDayIndex === day.weekDayIndex;
    },

    handleNextWeekClicked() {
        this.weekNumber += 1;
        this.loadWeekBar();
        this.filterHourRegistrations();
    },

    handlePrevWeekCLicked() {
      this.weekNumber -= 1;
      this.loadWeekBar();
      this.filterHourRegistrations();
    },

    handleAddActivityClicked() {
      this.showingModel = true
    },

    handleModelBackgroundClicked() {
      this.showingModel = false
    }
  }
}
</script>

<style scoped>

.day-container-item {
  @apply flex flex-col items-center w-[121px] rounded-[6px] transition-colors ease-in-out cursor-pointer;
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