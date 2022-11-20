<template>
  <div class="container main-container rounded-xl">
    <router-link :to="{name: 'project-overview', params: {projectId: project.id}}" class="flex mt-4 p-2 w-full justify-center">

      <img :src="project.logoSrc" alt="project logo" class="icon-container">

      <div class="flex flex-col justify-between container ml-4">
        <div class="flex flex-col mb-3">
          <div class="font-bold">{{ project.title }}</div>
          <div class="font-semibold text-neutral-500">{{ project.description }}</div>
        </div>

        <div class="container flex justify-between m-1">

          <stat :dot="false" icon="users" class="bottom-0">{{ project.participants.length }}</stat>

          <button class="bg-primary-400 rounded-md bold p-2 mr-2 h-[32px] flex items-center text-neutral-0">Bekijk
             Project
          </button>
        </div>
      </div>

    </router-link>
  </div>

</template>

<script>
import Stat from "./Stat.vue";

export default {
  components: {Stat},

  name: "ProjectListDetails",

  props: {
    project: {
      type: Object,
      required: true,
    }
  },

 created() {
    this.project.logoSrc = "/src/assets/ING-Bankieren-icoon.webp";

 }
}
</script>

<style scoped>

.main-container {
  background-color: #F5F7FA;
}


.icon-container {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  border-radius: 18px;
}

.icon-container > img {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  object-fit: cover;
  -o-object-fit: cover;
  border-radius: 18px;
  border: 4px solid #fff;
  background-color: #fff;
}
</style>