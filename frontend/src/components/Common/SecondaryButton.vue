<template>
  <button class="text-primary-500 font-semibold hover:bg-neutral-100 px-5 py-2.5 text-sm leading-5 rounded-lg w-fit">
    {{ title }}
  </button>

</template>

<script>
export default {
  name: "SecondaryButton",
  props: {
    title: String
  }
}
</script>

<style scoped>
.container {
  background: var(--primary-500);
}
</style>