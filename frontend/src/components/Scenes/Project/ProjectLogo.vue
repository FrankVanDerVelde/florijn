<template>
  <div class="icon-container grid w-full">
    <img :src="logoSrc" alt="project logo">
  </div>
</template>

<script>
export default {
  name: "ProjectLogo",
  inject: ["fetchService"],

  computed: {
    logoSrc() {
      if (this.project.logoSrc == null) return this.fetchService.getAsset('/projects/sample-logo.png');

      if (this.project.logoSrc.startsWith("data:image")) return this.project.logoSrc;
      return this.fetchService.getAsset(this.project.logoSrc);
    }
  },

  props: {
    project: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>

.icon-container {
  margin-top: -32px;
  z-index: 1;
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

@media screen and (min-width: 1024px) {
  .icon-container {
    margin-top: -52px;
  }

  .icon-container > img {
    margin-left: 48px;
    margin-right: unset;
    width: 88px;
    height: 88px;
    border-width: 8px;
    border-radius: 24px;
  }
}
</style>