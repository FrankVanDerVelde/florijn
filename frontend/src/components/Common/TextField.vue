<template>
  <div>
    <label v-if="label"
           for="first_name"
           class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300"
    >
      {{label}}
    </label>
    <input type="text" id="first_name" class="bg-neutral-50 border border-neutral-200 text-neutral-900 text-sm rounded-lg focus:ring-primary-300 focus:border-blue-500 block w-full p-2.5" :placeholder="placeholder" required>
  </div>
</template>

<script>
export default {
  name: "TextField",
  props: {
    label: String,
    placeholder: String
  }
}
</script>

<style scoped>

</style>