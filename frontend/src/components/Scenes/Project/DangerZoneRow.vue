<template>
  <li class="flex items-center gap-2 p-4 border-t border-t-neutral-100 first-of-type:border-t-0">
    <div class="flex-auto">
      <strong class="text-neutral-900 opacity-80">{{title}}</strong>
      <p class="text-sm">{{description}}</p>
    </div>

    <button type="button"
            @click="$emit('click')"
            class="rounded-md bg-neutral-50 px-3 py-1 font-medium text-app_red-500 border border-neutral-200 hover:bg-app_red-700 hover:text-neutral-0 hover:border-app_red-700 transition">
      {{button}}
    </button>
  </li>
</template>

<script>
export default {
  name: "DangerZoneRow",
  emits: ['click'],

  props: {
    title: {
      required: true
    },
    description: {
      required: true
    },
    button: {
      required: true
    }
  }
}
</script>

<style scoped>

</style>