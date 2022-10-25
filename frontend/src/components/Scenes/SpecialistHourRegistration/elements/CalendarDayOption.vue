<template>
  <div @click="$emit('dateClicked', dayName)"
       :class="isSelected ? 'selected-day-container' : 'unselected-day-container' ">
    <p class="font-medium" :class="isSelected ? 'text-neutral-0' : 'text-neutral-800'">
      {{ dayName }}</p>
    <p :class="isSelected ? 'text-neutral-0' : 'text-neutral-600' ">{{ date }}</p>
  </div>
</template>

<script>
export default {
  name: "CalenderDayOption",
  emits: ['dateClicked'],
  props: {
    dayName: String,
    date: String,
    isSelected: Boolean
  }
}
</script>

<style scoped>

</style>