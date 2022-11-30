<template>
  <div class="page-main-mw px-20 pt-[4em]">
    <div class="container flex flex-col">
      <div class="py-1">
        <p class="back-text" @click="buttonBackPage()">&lt Terug</p>
      </div>
      <div class="flex grid grid-cols-16">
        <div class="box flex justify-end col-span-7 p-2 w-full justify-center h-[120px]">
          <img :src="this.userData.avatarUrl" alt="profile picture" class="w-[82px] h-[82px] flex rounded-full mr-4">
          <div class="flex flex-col justify-between container ml-2">
            <div class="flex flex-col mb-3">
              <div class="container flex justify-between m-1">
                <div class="relative bottom-0">
                  <div class="font-bold flex">{{ this.userData.firstName + " " + this.userData.lastName }}</div>
                  <div class="flex font-semibold text-neutral-500">Developer</div>
                  <div class="flex mt-2">{{ this.userData.email }}</div>
                </div>
                <div>
                  <button id="addbutton"
                          class="bg-primary-500 border-[1px] h-[38px] w-[180px] text-sm rounded-md mr-2 text-neutral-0">
                    Op project zetten
                  </button>
                  <button
                      class="bg-neutral-50 border-neutral-200 mt-2 border-[1px] justify-center rounded-md bold p-2 mr-2 h-[38px] w-[180px] flex text-neutral-900">
                    CV downloaden
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box flex-col col-span-7 row-start-2 p-2 w-full mt-2">
          <div class="font-bold">Skills</div>
          <div class="my-2">
            <div class="flex-row ">Skill 1</div>
            <div class="flex-row ">Skill 2</div>
            <div class="flex-row ">Skill 3</div>
            <div class="flex-row ">Skill 4</div>
          </div>
        </div>

        <div class="box flex flex-col row-start-1 col-start-9 col-span-4 p-2 w-full h-full ">
          <div class="font-bold">Projecten</div>
          <div class="my-2">
            <div class="flex-row ">Project 1</div>
            <div class="flex-row ">Project 2</div>
            <div class="flex-row ">Project 3</div>
            <div class="flex-row ">Project 4</div>
            <div class="flex-row ">Project 5</div>
            <div class="flex-row ">Project 6</div>
            <div class="flex-row ">Project 7</div>
          </div>
        </div>
        <div class="box flex flex-col col-start-9 mt-2 col-span-4 p-2 w-full h-full ">
          <div class="font-bold">Beschikbaarheid</div>
          <div class="my-2 flex-row text-sm ">
            <div class="font-bold">Maandag:</div>
            <div class="font-bold">Dinsdag:</div>
            <div class="font-bold">Woensdag:</div>
            <div class="font-bold">Donderdag:</div>
            <div class="font-bold">Vrijdag:</div>
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

.box {
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