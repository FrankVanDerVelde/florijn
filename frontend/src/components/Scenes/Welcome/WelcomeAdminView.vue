<template>
  <div class="page-main-mw p-4 container">
    Dit is de admin home!! Ingelogd met id= {{ id }}
  </div>
</template>

<script>
export default {
  name: "WelcomeAdminView",
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