<template>
<button class="bg-primary-500 text-neutral-50 font-semibold hover:bg-primary-700 px-5 py-2.5 text-sm leading-5 rounded-lg w-fit">
  {{ title }}
</button>
  <p></p>
</template>

<script>

export default {
  name: "SecondaryButton",
  props: {
    title: String
  }
}
</script>