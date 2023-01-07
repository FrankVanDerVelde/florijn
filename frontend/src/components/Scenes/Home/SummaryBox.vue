<template>
  <HomeBox class="w-full !p-6">
    <div class="text-md font-semibold">{{title}}</div>
    <div class="text-xl">{{value}}</div>
  </HomeBox>
</template>

<script>
import HomeBox from "./HomeBox.vue";

export default {
  name: "SummaryBox",
  components: {HomeBox},

  props: {
    title: {
      type: String,
      required: true
    },
    value: {
      required: true
    }
  }
}
</script>