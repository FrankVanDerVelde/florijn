<template>
  <div>
    <div class="flex flex-col items-center bg-neutral-50 border-neutral-100 border-[1px] w-[121px] rounded-[6px]">
      <p class="font-medium text-neutral-800">Donderdag</p>
      <p class="text-neutral-600">20 juni</p>
    </div>

    <div class="flex flex-col items-center bg-neutral-50 border-neutral-100 border-[1px] w-[121px] rounded-[6px]">
      <p class="font-medium text-neutral-800">Donderdag</p>
      <p class="text-neutral-600">20 juni</p>
    </div>
  </div>
</template>

<script>
// import CalenderDayOption from "./elements/CalenderDayOption.vue";
export default {
  name: "CalenderDayOption",
  // components: [CalenderDayOption],
  props: {
    dayName: String,
    date: String
  }
}
</script>

<style scoped>

</style>