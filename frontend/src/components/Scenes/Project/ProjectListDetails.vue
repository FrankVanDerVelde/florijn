<template>
  <div class="w-full main-container rounded-xl">
    <router-link :to="{name: 'project-overview', params: {projectId: project.id}}"
                 class="flex mt-4 p-2 w-full justify-center">

      <Asset :src="logoSrc" alt="project logo" class="icon-container"/>

      <div class="flex flex-col justify-between w-full ml-4">
        <div class="flex flex-col mb-3">
          <div class="font-bold">{{ project.title }}</div>
          <div class="font-semibold text-neutral-500">{{ project.description }}</div>
        </div>

        <div class="w-full flex justify-between m-1">

          <div class="flex flex-row">
            <stat :dot="false" icon="users" class="bottom-0">{{ project.participants.length }}</stat>
            <stat v-if="project.archived" :dot="false" icon="box-archive">Gearchiveerd</stat>
          </div>

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
import Asset from "../../Common/Asset.vue";

export default {
  components: {Asset, Stat},
  name: "ProjectListDetails",
  inject: ["fetchService"],

  computed: {
    logoSrc() {
      if (this.project.logoSrc == null) return '/projects/sample-logo.png';
      return this.project.logoSrc;
    }
  },

  props: {
    project: {
      type: Object,
      required: true,
    }
  },

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