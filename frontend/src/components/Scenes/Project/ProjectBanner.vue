<template>
  <div class="banner-container grid">
    <Asset :src="project.client?.bannerSrc ?? 'defaults/default-banner.webp'" :class="{'max-h-[200px]': preview}" alt="banner"/>
  </div>
</template>

<script>
import Asset from "../../Common/Asset.vue";

export default {
  name: "ProjectBanner",
  components: {Asset},

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