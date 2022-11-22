<template>
  <div class="page-main-mw p-4 container">
    Dit is de specialist home!! Ingelogd met id= {{ id }}
  </div>
</template>

<script>
export default {
  name: "WelcomeSpecialistView",
  created() {
    if (this.id === "null") {
      this.$router.push("/home");
    }
  },
  data() {
    return {
      id: localStorage.getItem("id")
    }
  }
}
</script>

<style scoped>

</style>