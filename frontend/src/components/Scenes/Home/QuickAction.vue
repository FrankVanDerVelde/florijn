<template>
  <router-link :to="link" class="flex flex-row md:flex-col items-center md:max-w-[200px] gap-3 md:gap-2 p-2 md:p-4 rounded-xl transition hover:bg-gray-50">
    <div class="bg-primary-50 w-10 md:w-14 w-10 h-10 md:h-14 text-primary-400 rounded-full flex items-center justify-center">
      <font-awesome-icon :icon="icon"/>
    </div>
    <div class="font-medium text-gray-600 text-center">{{title}}</div>
  </router-link>
</template>

<script>
export default {
  name: "QuickAction",

  props: {
    icon: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    link: {
      required: true,
    },
  }
}
</script>

<style scoped>

</style>