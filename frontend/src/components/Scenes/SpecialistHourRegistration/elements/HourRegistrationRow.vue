<template>
  <div class="bg-neutral-0 rounded-[10px] hour-registration-row-shadow border-l-[12px] border-neutral-100 border-l-primary-500 w-full">
    <div class="py-[13px] px-[12px] flex justify-between">
      <div @click="handleRowClicked" class="flex flex-col w-full cursor-pointer">
        <p class="font-medium text-neutral-800">{{ hourRegistration.project.title }}</p>
        <div class="flex items-center gap-2 text-neutral-800">
          <font-awesome-icon icon="clock"/>
          <p>{{ hourRegistration.formattedFromToTime() }}</p>
        </div>
      </div>
      <div v-if="!hourRegistration.isLocked()" class="flex gap-4">
        <div class="w-[26px] h-[26px]" @click="handleEditClicked">
          <font-awesome-icon class="text-xl text-neutral-900 cursor-pointer" icon="fa-solid fa-pen"/>
        </div>
        <div class="w-[26px] h-[26px]" @click="handleDeleteHourRegistrationClicked(hourRegistration.id)">
          <font-awesome-icon class="text-app_red-500 text-xl cursor-pointer" icon="fa-solid fa-trash-can"/>
        </div>
      </div>
      <div v-else>
        <div class="" @click="handleRowClicked">
          <div v-if="this.hourRegistration.isApproved()" class="flex items-center gap-2 bg-app_green-100 px-2 rounded">
            <font-awesome-icon class="text-app_green-500 cursor-pointer" icon="fa-solid fa-check"/>
            <p class="text-app_green-500 font-medium">Geaccepteerd</p>
          </div>

          <div v-if="this.hourRegistration.isRejected()" class="flex items-center gap-2 bg-app_red-100 px-2 rounded">
            <font-awesome-icon class="text-app_red-500 cursor-pointer" icon="fa-solid fa-xmark"/>
            <p class="text-app_red-500 font-medium">Geweigerd</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {HourRegistration} from "../../../models/HourRegistration/HourRegistration.js";

export default {
  name: "HourRegistrationRow",
  emits: ['delete-hour-registration-clicked', 'row-clicked'],
  props: {
    hourRegistration: HourRegistration
  },
  methods: {
    handleDeleteHourRegistrationClicked(id) {
      this.$emit('delete-hour-registration-clicked', id);
    },

    handleEditClicked() {
      this.$emit('row-clicked');
    },

    handleRowClicked() {
        this.$emit('row-clicked');
    }
  }
}
</script>