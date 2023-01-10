<template>
  <div class="flex flex-row items-center w-max">
    <Asset :src="src" alt="Avatar" class="w-[32px] h-[32px] mr-2" :class="[roundImage ? 'rounded-full' : 'rounded-md']"/>
    <div class="flex flex-col justify-between">
      <div class="font-bold text-sm">{{ title }}</div>
      <div class="font-semibold text-neutral-500 text-sm" v-if="subtitle">{{ subtitle }}</div>
    </div>
  </div>
</template>

<script>
import Asset from "./Asset.vue";

export default {
  name: "SmallTableCard",
  components: {Asset},

  props: {
    src: {
      type: String,
      required: true
    },
    title: {
      type: String,
      required: true
    },
    subtitle: {
      type: String,
      required: true
    },
    roundImage: {
      type: Boolean,
      default: true
    }
  }
}
</script>

<style scoped>

</style>