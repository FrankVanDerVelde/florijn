<template>
  <img :src="logoSrc" :alt="alt"/>
</template>

<script>
export default {
  name: "Asset",
  inject: ['assetsService'],

  computed: {
    logoSrc() {
      if (this.src.startsWith("data:image")) return this.src;
      if (this.src.startsWith("http")) return this.src;

      return this.assetsService.getAsset(this.src);
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