<template>
  <div class="col-span-6 md:col-span-3 w-full h-full min-h-[150px] bg-neutral-50 p-4 flex flex-col justify-center rounded-2xl">
    <div class="font-bold text-neutral-800 text-3xl">{{value}}</div>
    <div class="text-neutral-600">{{label}}</div>
  </div>
</template>

<script>
export default {
  name: "SummaryBlock",

  props: {
    value: {
      type: Number,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
  }
}
</script>

<style scoped>
</style>