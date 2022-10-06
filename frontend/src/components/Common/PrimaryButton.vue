<template>
<button class="bg-primary-500 hover:bg-primary-700 px-5 py-2.5 text-sm leading-5 rounded-lg font-semibold text-white w-fit">{{ title }}</button>
</template>

<script>

export default {
  name: "SecondaryButton",
  props: {
    title: String
  }
}
</script>