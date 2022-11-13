<template>
<div>smash</div>

  {{skillset}}
</template>

<script>
export default {
  name: "FilterParticipants",

  props: {
    skillset: {
      type: Array
    },
    index: {
      type: Number
    }


  }
}
</script>

<style scoped>

</style>