<template>
  <div class="page-main-mw px-20 pt-[4em]">
    <div class="container flex flex-col">
      <div class="py-1">
        <p class="back-text" @click="buttonBackPage()">&lt Terug</p>
      </div>
      <div class="flex grid grid-cols-16">

        <div class="personal-info flex justify-end col-span-7 p-2 w-full justify-center h-[150px]">

          <img :src="this.userData.avatarUrl" alt="profile picture" class="w-[82px] h-[82px] rounded-full mr-4">
          <div class="flex flex-col justify-between container ml-2">
            <div class="flex flex-col mb-3">
              <div class="container flex justify-between m-1">
                <div class="relative bottom-0">
                  <div class="font-bold">{{ this.userData.firstName + " " + this.userData.lastName }}</div>
                  <div class="font-semibold text-neutral-500">Developer</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="personal-info flex-col col-start-9 col-span-4 p-2 w-full h-full ">
          <div class="font-bold">Projecten</div>
          <div class="my-2">
            <div class="flex-row ">Project 1</div>
            <div class="flex-row ">Project 1</div>
            <div class="flex-row ">Project 1</div>
            <div class="flex-row ">Project 1</div>
            <div class="flex-row ">Project 1</div>
            <div class="flex-row ">Project 1</div>
            <div class="flex-row ">Project 1</div>
          </div>
        </div>


      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PublicProfile",
  inject: ['fetchService'],
  data() {
    return {
      specialistId: '',
      userData: '',
    }
  },
  created() {
    console.log(this.userData)
    if (this.id === "null" || localStorage.getItem("role") === "specialist") {
      this.$router.push("/home");
    }
    this.specialistId = this.$route.params.Id;
    this.getUserInfo();
    console.log(this.userData)
  },
  methods: {
    async getUserInfo() {
      this.userData = await this.fetchService.fetchUrl("/user/" + this.specialistId);
    },
    buttonBackPage() {
      if (localStorage.getItem("role") === "admin") {
        this.$router.push("/adminpanel/employee-list");
      } else {
        this.$router.push("/client/home");
      }
    }
  }
}

</script>

<style scoped>

.personal-info {
  border: 1px solid var(--neutral-100);
  border-radius: 0.75rem;
}

.back-text {
  color: var(--primary-500);
  width: 50px;
  margin: 1%;
  font-weight: bold;
  font-size: 14px;
}


</style>