<template>
  <div class="bg-neutral-0 rounded-[10px] hour-registration-row-shadow border-l-[12px] border-neutral-100 border-l-primary-500 w-full">
    <div class="py-[13px] px-[12px] flex justify-between">
      <div class="flex flex-col">
        <p class="font-medium text-neutral-800">{{ hourRegistration.project.name }}</p>
        <div class="flex items-center gap-2 text-neutral-800">
          <font-awesome-icon icon="clock"/>
          <p>{{ hourRegistration.formattedFromToTime() }}</p>
        </div>
      </div>
      <div class="flex">
        <div class="w-[26px] h-[26px]" @click="handleDeleteHourRegistrationClicked(hourRegistration.id)">
          <font-awesome-icon class="text-app_red-500 text-xl" icon="fa-solid fa-trash-can"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {HourRegistration} from "../../../models/HourRegistration.js";

export default {
  name: "HourRegistrationRow",
  emits: ['delete-hour-registration-clicked'],
  props: {
    hourRegistration: HourRegistration
  },
  methods: {
    handleDeleteHourRegistrationClicked(id) {
      console.log(id);
      this.$emit('delete-hour-registration-clicked', id);
    }
  }
}
</script>