<template>
  <div class="flex flex-col z-90 bg-neutral-0 p-[16px] rounded-[10px]">
    <p class="text-xl font-medium">Nieuwe activiteit</p>
    <hr>

    <form @submit.stop class="flex flex-col gap-4 pt-8">
      <div class="form-row">
        <label id="project" class="font-semibold">Project</label>
        <select v-model="selectedProjectId" v-for="project in projects" name="projects" id="cars">
          <option :value="project.id">{{project.title}}</option>
        </select>
      </div>

      <div class="form-row">
        <label class="font-semibold">Van</label>
        <input v-model="from" type="time">
      </div>

      <div class="form-row">
        <label class="font-semibold">Tot</label>
        <input v-model="to" type="time">
      </div>

      <div class="form-row">
        <label class="font-semibold">Beschrijving</label>
        <textarea v-model="description" class="border-[1px] rounded p-2 border-neutral-100" type="text" placeholder="beschrijving"></textarea>
      </div>

      <div class="flex gap-2">
        <button @click="$emit('activity-cancel-clicked')" class="secondary-button">Annuleren</button>
        <button @click="handleSaveTapped" class="primary-button">Opslaan</button>
      </div>
    </form>
  </div>
</template>

<script>
import moment from "moment/moment.js";
import {HourRegistration} from "../../../models/HourRegistration.js";

export default {
  name: "NewActivityPopup",
  inject: ['projectFetchService', 'hourRegistrationRepository'],
  emits: ['dismiss-clicked', 'activity-added', 'activity-cancel-clicked'],
  props: {
    hourRegistration: HourRegistration
  },
  data() {
    return {
      projects: [],
      selectedProjectId: null,
      from: null,
      to: null,
      description: null
    }
  },
  async created() {
    await this.loadProjects();
    if (this.hourRegistration) {
      this.selectedProjectId = this.hourRegistration.id;
      this.from = moment(this.hourRegistration.from).format('hh:mm');
      this.to = moment(this.hourRegistration.to).format('hh:mm');
      console.log(this.hourRegistration)
      this.description = this.hourRegistration.description
    }
  },

  methods: {
    async loadProjects() {
      this.projects = await this.projectFetchService.fetchJson('/');
      console.log(this.projects);
    },
    async handleSaveTapped() {
      if (this.hourRegistration) {
        await this.updateHourRegistration();
      } else {
        await this.createNewHR()
      }
    },

    async createNewHR() {
      try {
        await this.hourRegistrationRepository
            .create(
                this.selectedProjectId,
                0,
                this.convertTimeToDateString(this.from),
                this.convertTimeToDateString(this.to),
                this.description
            );
        this.$emit('activity-added');
      } catch (e) {
        console.error(e);
      }
    },

    async updateHourRegistration() {
        try {
          await this.hourRegistrationRepository
              .update(
                  this.hourRegistration.id,
                  this.hourRegistration.project.id,
                  this.convertTimeToDateString(this.from),
                  this.convertTimeToDateString(this.to),
                  this.description
              );
          this.$emit('activity-added');
        } catch (e) {
          console.error(e);
        }
    },

    convertTimeToDateString(timeStringInHoursMinutes) {
      return moment(timeStringInHoursMinutes, 'hh:mm').format('yyyy-MM-DD HH:mm');
    }
  }
}
</script>

<style scoped>
.form-row {
  @apply flex flex-col gap-1;
}
</style>