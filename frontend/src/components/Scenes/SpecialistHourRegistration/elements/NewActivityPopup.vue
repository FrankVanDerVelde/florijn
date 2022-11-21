<template>
  <div class="flex flex-col z-90 bg-neutral-0 p-[16px] rounded-[10px]">
    <p class="text-xl font-medium">Nieuwe activiteit</p>
    <hr>

    <div class="flex flex-col gap-4 pt-8">

      <div class="form-row">
        <label id="project" class="font-semibold">Project</label>
        <select v-for="project in projects" name="projects" id="cars">
          <option :value="project.id">{{project.title}}</option>
        </select>
      </div>

      <div class="form-row">
        <label class="font-semibold">Van</label>
        <input type="datetime-local">
      </div>

      <div class="form-row">
        <label class="font-semibold">Tot</label>
        <input type="datetime-local">
      </div>

      <div class="form-row">
        <label class="font-semibold">Beschrijving</label>
        <textarea class="border-[1px] rounded p-2 border-neutral-100" type="text" placeholder="beschrijving"></textarea>
      </div>
      <div class="flex gap-2">
        <button @click="" class="secondary-button">Annuleren</button>
        <button @click="" class="primary-button">Opslaan</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewActivityPopup",
  inject: ['projectFetchService'],
  emits: ['dismiss-clicked'],
  data() {
    return {
      projects: []
    }
  },
  async created() {
    await this.loadProjects();
  },
  methods: {
    async loadProjects() {
      this.projects = await this.projectFetchService.fetchJson('/');
      console.log(this.projects);
    },
  }
}
</script>

<style scoped>
.form-row {
  @apply flex flex-col gap-1;
}
</style>