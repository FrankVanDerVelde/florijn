<template>
  <div class="banner-container grid">
    <img :src="project.client?.bannerSrc ?? '/src/assets/banner-default.webp'" :class="{'max-h-[200px]': preview}" alt="banner">
  </div>
</template>

<script>
export default {
  name: "ProjectBanner",

  props: {
    project: {
      type: Object,
      required: true
    },
    preview: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>

.banner-container {
  width: 100%;
}

.banner-container > img {
  background-color: #d9d9d9;
  grid-column: span 12;
  width: 100%;
  height: 218px;
  object-fit: cover;
  -o-object-fit: cover;
  border-radius: 16px;
}

@media screen and (min-width: 1024px) {
  .banner-container > img {
    height: 400px;
  }
}
</style>