<template>
  <div class="flex justify-center pt-20">
    <div class="flex flex-col gap-[11px] w-2/3">
      <div class="flex justify-center gap-[11px]">
        <div v-for="day in currentWeek">
          <div @click="handleDateClicked(day)"
               :class="isSelectedForDay(day) ? 'selected-day-container' : 'unselected-day-container' ">
            <p class="font-medium" :class="!isSelectedForDay(day) ? 'text-neutral-800' : 'text-neutral-0'">
              {{ day.dayName }}</p>
            <p :class="!isSelectedForDay(day) ? 'text-neutral-600' : 'text-neutral-0'">{{ day.formattedDate }}</p>
          </div>
        </div>
      </div>
      <div class="w-full h-[31px] bg-primary-50 rounded-[9px] text-primary-500 font-semibold flex justify-center items-cente cursor-pointer">+ Toevoegen</div>
      <div class="flex flex-col gap-4">
        <div v-for="hourRegistration in hourRegistrations" class="">
          <div class="bg-neutral-0 rounded-[10px] hour-registration-row-shadow border-l-[12px] border-neutral-100 border-l-primary-500">
            <div class="py-[13px] pl-[12px] flex flex-col ">
              <p class="font-medium text-neutral-800">{{ hourRegistration.title }}</p>
              <div class="flex">
                <p class="text-neutral-800">{{ hourRegistration.time }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HourRegistrationOverview",
  data() {
    return {
      currentWeek: [
        {
          dayName: "Maandag",
          formattedDate: "20 juni",
        },
        {
          dayName: "Dinsdag",
          formattedDate: "21 juni",
        },
        {
          dayName: "Woensdag",
          formattedDate: "22 juni",
        },
        {
          dayName: "Donderdag",
          formattedDate: "23 juni",
        },
        {
          dayName: "vrijdag",
          formattedDate: "24 juni",
        },
        {
          dayName: "Zaterdag",
          formattedDate: "25 juni",
        },
        {
          dayName: "Zondag",
          formattedDate: "26 juni",
        }
      ],
      hourRegistrations: [
        {
          title: "Project EWA FLorijn",
          time: "12:00 - 14:45"
        },
        {
          title: "Project SMA",
          time: "15:00 - 16:00"
        },
        {
          title: "Project APL",
          time: "16:00 - 17:00"
        },
      ],
      selectedDayName: null
    }
  },
  methods: {
    handleDateClicked(day) {
      this.selectedDayName = day.dayName;
    },
    isSelectedForDay(day) {
      return this.selectedDayName === day.dayName;
    }
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