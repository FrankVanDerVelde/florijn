<template>
  <div class="flex flex-col z-90 bg-neutral-0 py-[16px] rounded-[10px]">
    <div class="text-xl font-semibold mb-2 px-[16px]">
      <p v-if="!isLocked">
        {{ this.hourRegistration ? 'Activiteit bijwerken' : 'Nieuwe activiteit' }}
      </p>
      <p v-else>Activiteit inzien</p>
    </div>
    <hr class="text-neutral-300">

    <div class="flex flex-col pt-4 px-[16px]">
      <div v-if="isApproved" class="flex flex-col justify-center items-center gap-2">
        <font-awesome-icon icon="fa-solid fa-circle-check" class="text-2xl text-app_green-500" />
        <p class="text-app_green-500 font-medium text-xl">Geaccepteerd</p>
      </div>

      <div v-if="isDeclined" class="flex flex-col justify-center items-center gap-2">
        <font-awesome-icon icon="fa-solid fa-circle-xmark" class="text-2xl text-app_red-500" />
        <p class="text-app_red-500 font-medium text-xl">Afgewezen</p>
      </div>

      <form @submit.prevent class="flex flex-col gap-4">
        <div class="form-row">
          <label id="project" class="font-semibold">Project</label>
          <select v-model="form.selectedProjectId" :disabled="isLocked" class="border border-neutral-300 p-2 rounded rounded-[4px]">
            <option v-if="!this.hourRegistration" disabled :selected="form.selectedProjectId === null" :value="null">Kies een project</option>
            <option v-for="project in projects" :key="project.id"
                    :selected="project.id === form.selectedProjectId"
                    :value="project.id"
                    :disabled="project.archived"
            >
              {{ project.title }} {{ project.archived ? '(Gearchiveerd)' : '' }}
            </option>
          </select>
          <p v-if="errors.projectSelection" class="validation-label">{{ errors.projectSelection }}</p>
        </div>

        <div class="form-row">
          <label class="font-semibold">Van</label>
          <input :disabled="isLocked"
                 v-model="form.from"
                 type="time"
                 class="border border-neutral-300 p-2 rounded rounded-[4px]">
          <p v-if="errors.from" class="validation-label">{{ errors.from }}</p>
        </div>

        <div class="form-row">
          <label class="font-semibold">Tot</label>
          <input :disabled="isLocked"
                 v-model="form.to"
                 type="time"
                 class="border border-neutral-300 p-2 rounded rounded-[4px]">
          <p v-if="errors.to" class="validation-label">{{ errors.to }}</p>
        </div>

        <div class="form-row">
          <label class="font-semibold">Beschrijving</label>
          <textarea :disabled="isLocked"
                    v-model="form.description"
                    class="border-[1px] rounded p-2 border-neutral-300"
                    type="text" placeholder="beschrijving">
        </textarea>
          <p v-if="errors.description" class="validation-label">{{ errors.description }}</p>
        </div>

        <div v-if="!isLocked" class="flex gap-2 justify-center">
          <button @click="$emit('activity-cancel-clicked')" class="secondary-button grow">Annuleren</button>
          <button @click="handleSaveTapped" class="primary-button grow">Opslaan</button>
        </div>
        <div v-else class="flex">
          <button @click="$emit('activity-cancel-clicked')" class="secondary-button grow">Sluiten</button>
        </div>
        <p v-if="errors.submitError" class="validation-label">{{ errors.submitError }}</p>
      </form>
    </div>
  </div>
</template>

<script>
import moment from "moment/moment.js";
import {HourRegistration} from "../../../models/HourRegistration/HourRegistration.js";

export default {
  name: "NewActivityPopup",
  inject: ['projectRepository', 'storedTokenRepository', 'hourRegistrationRepository'],
  emits: ['dismiss-clicked', 'activity-added', 'activity-cancel-clicked'],
  props: {
    hourRegistration: HourRegistration,
    weekNumber: Number,
    selectedDayIndex: Number,
  },
  data() {
    return {
      projects: [],
      form: {
        selectedProjectId: null,
        from: null,
        to: null,
        description: null,
      },
      date: null,
      errors: {
        projectSelection: null,
        from: null,
        to: null,
        description: null,
        submitError: null,
      },
      isFormValid: null,
      isLocked: false,
      isApproved: false,
      isDeclined: false,
    }
  },

  async created() {
    if (this.hourRegistration) {
      this.date = moment(this.hourRegistration.from).startOf('day');
      this.form.selectedProjectId = this.hourRegistration.project.id;
      this.form.from = moment(this.hourRegistration.from).format('HH:mm');
      this.form.to = moment(this.hourRegistration.to).format('HH:mm');
      this.form.description = this.hourRegistration.description;

      this.isApproved = this.hourRegistration.isApproved();
      this.isDeclined = this.hourRegistration.isRejected();
      this.isLocked = this.hourRegistration.isLocked();
    } else {
      this.date = moment()
          .isoWeek(this.weekNumber)
          .isoWeekday(this.selectedDayIndex);
    }
    await this.loadProjects();
  },

  computed: {
    userId() {
      return JSON.parse(localStorage.getItem('user')).id;
    }
  },

  methods: {
    async loadProjects() {
      this.projects = await this.projectRepository.fetchProjects();
    },

    async handleSaveTapped() {
      this.validateForm();
      if (!this.isFormValid)
        return;

      if (this.hourRegistration) {
        await this.updateHourRegistration();
      } else {
        await this.createNewHR();
      }
    },

    async createNewHR() {
      try {
        await this.hourRegistrationRepository
            .create(
                this.form.selectedProjectId,
                this.userId,
                this.convertTimeToDateString(this.form.from),
                this.convertTimeToDateString(this.form.to),
                this.form.description
            );
        this.$emit('activity-added');
      } catch (e) {
        console.error(e);
        this.errors.submitError = 'Something went wrong creating the hour registration, please try again later';
      }
    },

    async updateHourRegistration() {
      try {
        await this.hourRegistrationRepository
            .update(
                this.hourRegistration.id,
                this.hourRegistration.project.id,
                this.userId,
                this.convertTimeToDateString(this.form.from),
                this.convertTimeToDateString(this.form.to),
                this.form.description
            );
        this.$emit('activity-added');
      } catch (e) {
        console.error(e);
        this.errors.submitError = 'Something went wrong updating the hour registration, please try again later';
      }
    },

    validateForm() {
      this.isFormValid = null;
      this.resetValidationErrors();

      this.validateSelectedProject();

      this.validateFrom();
      this.validateTo();
      this.validateCorrectTimeOrder();

      this.validateDescription();

      if (this.isFormValid === null) {
        this.isFormValid = true;
      }
    },

    validateSelectedProject() {
      if (!this.form.selectedProjectId) {
        this.isFormValid = false;
        this.errors.projectSelection = 'Kies een project';
      }
    },

    validateFrom() {
      if (!this.form.from) {
        this.isFormValid = false;
        this.errors.from = 'Vul een begintijd in.';
      }
    },

    validateTo() {
      if (!this.form.to) {
        this.isFormValid = false;
        this.errors.to = 'Vul een eindtijd in.';
      }
    },

    validateCorrectTimeOrder() {
      if (this.form.from && this.form.to) {
        const from = moment(this.form.from, 'hh:mm');
        const to = moment(this.form.to, 'hh:mm');
        if (from.isSame(to)) {
          this.isFormValid = false;
          this.errors.submitError = "Tijden zijn hetzelfde";
        } else if (from.isAfter(to)) {
          this.isFormValid = false;
          this.errors.submitError = "De aangegeven begintijd is na de startijd.";
        }
      }
    },

    validateDescription() {
      if (!this.form.description) {
        this.isFormValid = false;
        this.errors.description = 'Vul een beschrijving in';
      }
    },

    resetValidationErrors() {
      Object.keys(this.errors).forEach(i => this.errors[i] = null);
    },

    convertTimeToDateString(timeStringInHoursMinutes) {
      // Create flowing format: 'yyyy-MM-DD HH:mm'
      return `${this.date.format('yyyy-MM-DD')} ${timeStringInHoursMinutes}`;
    }
  }
}
</script>

<style scoped>
.form-row {
  @apply flex flex-col gap-1;
}

.validation-label {
  @apply text-red-500
}
</style>