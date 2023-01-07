<template>
  <HomeBox>
    <h2 class="text-xl font-semibold mb-2">{{label}}</h2>

    <div v-if="projectCount === 0" class="flex gap-2 items-center text-app_red-500">
      <font-awesome-icon icon="fa-face-grin-squint" size="xl"></font-awesome-icon>
      <p>{{ errorMessage}}</p>
    </div>
    <router-link v-for="project in projects" :to="{name: 'project', params: {projectId: project.id}}"
                 class="project p-2 rounded-md flex items-center gap-2 transition hover:bg-neutral-75"
                 :key="project.id">
      <Asset :src="project.logoSrc" class="rounded-md h-8 w-8 object-contain"/>
      <div class="flex flex-col ml-2">
        <h3 class="text-lg font-medium">{{ project.title }}</h3>
      </div>
    </router-link>
  </HomeBox>
</template>

<script>
import Asset from "../../Common/Asset.vue";
import HomeBox from "./HomeBox.vue";

export default {
  name: "HomeProjectListBox",
  components: {HomeBox, Asset},

  props: {
    projects: {
      type: Array,
      required: true
    },
    projectCount: {
      type: Number,
      required: true
    },
    label: {
      type: String,
      required: true
    },
    errorMessage: {
      type: String,
      required: true,
    }
  },
}
</script>

<style scoped>

</style>