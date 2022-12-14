<template>
  <div class="container main-container rounded-xl">
    <div class="flex mt-4 p-2 w-full justify-center">
      <Asset :src="logoSrc" alt="project logo" class="icon-container"/>
      <div class="flex flex-col container ml-4 justify-center">
        <div class="flex flex-col">
          <div class="font-medium">{{ project.title }}</div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import Asset from "../../Common/Asset.vue";

export default {
  components: {Asset},
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


.icon-container {
  margin-left: auto;
  margin-right: auto;
  width: 50px;
  height: 50px;
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
}
</style>