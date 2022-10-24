<template>
  <div class="stats flex flex-row">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "Stats"
}
</script>

<style scoped>
  .stats {
    padding-bottom: 24px;
  }
</style>