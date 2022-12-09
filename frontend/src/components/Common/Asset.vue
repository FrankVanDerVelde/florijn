<template>
  <img :src="logoSrc" :alt="alt"/>
</template>

<script>
export default {
  name: "Asset",
  inject: ['fetchService'],

  computed: {
    logoSrc() {
      if (this.src.startsWith("data:image")) return this.src;

      return this.fetchService.getAsset(this.src);
    }
  },

  props: {
    src: {
      type: String,
      required: true
    },
    alt: {
      type: String,
      required: false,
      default: "Asset"
    }
  }
}
</script>

<style scoped>

</style>