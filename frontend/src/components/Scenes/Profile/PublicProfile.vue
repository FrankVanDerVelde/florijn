<template>
  <div class="page-main-mw px-20 pt-[4em]">
    <div class="container flex flex-col">
      <div class="py-0.5">
        <p class="back-text cursor-pointer" @click="buttonBackPage()">&lt Terug</p>
      </div>
      <div class="flex grid grid-cols-16">
        <div class="flex flex-col row-start-1 col-span-7 p-2 w-full">
          <div class="box flex  p-2 justify-center h-[120px]">
            <Asset :src="this.userData.avatarUrl??'defaults/default-avatar.png'" alt="profile picture"
                   class="w-[82px] h-[82px] flex rounded-full mr-4"></Asset>
            <div class="flex flex-col justify-between container ml-2">
              <div class="flex flex-col mb-3">
                <div class="container flex justify-between m-1">
                  <div class="relative bottom-0">
                    <div class="font-bold flex">{{ userData.firstName + " " + userData.lastName }}</div>
                    <div class="flex font-semibold text-neutral-500">Developer</div>
                    <div class="flex mt-2">{{ userData.email }}</div>
                  </div>
                  <div v-if="this.user.role === 'ADMIN'">
                    <button @click="viewResume()" class="bg-neutral-50 border-neutral-200 mt-2 border-[1px] justify-center
                    rounded-md bold p-2 mr-2 h-[38px] w-[180px] flex text-neutral-900">CV bekijken
                    </button>
                    <div v-if="this.showResumeFailText"
                         class="mt-1 justify-center text-app_red-500 p-2 mr-2 h-[38px] w-[180px] flex ">Geen CV
                      beschikbaar
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box flex-col p-2 w-full mt-3">
            <div class="pb-3">
              <div v-if="this.skills !== []" class="back-text float-right cursor-pointer" @click="toggleSkillList()">{{ showSkillsTitle }}</div>
              <div class="font-bold ml-2">Skills</div>
            </div>
            <div class="my-2 ml-2">
              <div v-if="this.skills.length === 0">Er zijn geen skills gevonden voor {{ this.userData.firstName }}</div>
              <skill-list-summary v-if="showTempSkills === true" v-for="skill in tempSkills" :key="skill.id"
                                  :skill="skill"/>
              <skill-list-summary v-else v-for="skill in skills" :key="skill.id" :skill="skill"/>
            </div>
          </div>
        </div>
        <div class="flex flex-col row-start-1 col-start-9 col-span-4 p-2 w-full h-full">
          <div class="box flex flex-col p-2">
            <div class="font-bold">Projecten</div>
            <project-list-details-summary v-for="project in projects" :key="project.id" :project="project"/>
            <div class="my-2" v-if="this.projects.length === 0">Er zijn geen projecten gevonden voor
              {{ this.userData.firstName }}
            </div>
          </div>
          <div class="box flex flex-col mt-3 p-2">
            <div class="font-bold">Beschikbaarheid</div>
            <div class="my-2 av-error" v-if="this.availability.length === 0">Er is geen beschikbaarheid gevonden voor
              {{ this.userData.firstName }}
            </div>
            <div class="my-2 flex-row text-sm" v-else>
              <div>
                <div class="font-bold float-left w-[96px]">Maandag:</div>
                <div>{{ this.monAv }}</div>
              </div>
              <div>
                <div class="font-bold float-left w-[96px]">Dinsdag:</div>
                <div>{{ this.tueAv }}</div>
              </div>
              <div>
                <div class="font-bold float-left w-[96px]">Woensdag:</div>
                <div>{{ this.wedAv }}</div>
              </div>
              <div>
                <div class="font-bold float-left w-[96px]">Donderdag:</div>
                <div>{{ this.thuAv }}</div>
              </div>
              <div>
                <div class="font-bold float-left w-[96px]">Vrijdag:</div>
                <div>{{ this.friAv }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-if="modalActive" class="bg-white absolute top-0 bottom-0 left-0 right-0 z-10 ">

      <object v-if="pdfSrc" :data="pdfSrc" class="w-full h-full pt-[60px]" type="application/pdf">

      </object>

      <div class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex justify-center items-center rounded-md w-[120px] h-[38px] mb-[10px] hover:bg-primary-600 capitalize font-bold text-[14px] text-center fixed bottom-0 left-1/2 -translate-x-1/2"
           @click="toggleModal">
        <div>Close window</div>
      </div>
    </div>

  </div>
</template>

<script>

import ProjectListDetailsSummary from "../../Scenes/Project/ProjectsListDetailsSummary.vue";
import SkillListSummary from "../../Scenes/Project/SkillListSummary.vue";
import Asset from "../../Common/Asset.vue";

export default {
  name: "PublicProfile",
  inject: ['assetsService', 'dateService', 'userRepository', 'projectRepository', 'skillsRepository', 'availabilityRepository'],
  components: {ProjectListDetailsSummary, SkillListSummary, Asset},
  computed: {
    user() {
      return JSON.parse(localStorage.getItem('user')) ?? {};
    },
    pdfSrc() {
      return this.assetsService.getAsset(this.resumeURL);
    }
  },
  data() {
    return {
      specialistId: '',
      userData: [],
      projects: [],
      skills: [],
      tempSkills: [],
      availability: [],
      monAv: '-',
      tueAv: '-',
      wedAv: '-',
      thuAv: '-',
      friAv: '-',
      showResumeFailText: false,
      modalActive: false,
      resumeURL: null,
      showTempSkills: true,
      showSkillsTitle: 'Meer weergeven',
    }
  },
  created() {
    if (Object.keys(this.user).length === 0) {
      this.$router.replace({path: '/login'})
    }
    this.specialistId = this.$route.params.Id;
    this.fetchUserInfo();
    this.fetchProjects();
    this.fetchSkills();
    this.fetchAvailability();
  },
  methods: {
    async fetchUserInfo() {
      this.userData = await this.userRepository.getUserById(this.specialistId);
    },
    async fetchProjects() {
      this.projects = await this.projectRepository.fetchProjectByUserId(this.specialistId);

      if (this.projects == null) {
        this.projects = [];
      }
    },
    async fetchSkills() {
      this.skills = await this.skillsRepository.fetchUserSkills(this.specialistId);
      if (this.skills == null) {
        this.skills = [];
      }

      if (this.skills.length > 8) {
        this.tempSkills = this.skills.slice(0, 8);
      }
    },
    async toggleModal() {
      this.modalActive = !this.modalActive;
    },
    async viewResume() {
      const resumeData = await this.userRepository.getResumeById(this.specialistId);
      this.resumeURL = resumeData.resumeURL

      if (this.resumeURL == null) {
        this.showResumeFailText = true;
        return false;
      }
      await this.toggleModal();
      this.showResumeFailText = false;
      return true;
    },
    async fetchAvailability() {
      const weekNumber = this.dateService.currentWeekOfYear();
      const year = this.dateService.currentYear();
      this.availability = await this.availabilityRepository.fetchAvailabilityForUserInWeek(this.specialistId, weekNumber, year)

      for (let i = 0; i < this.availability.length; i++) {
        const dayIndex = this.dateService.dayIndex(this.availability[i].date)
        this.availabilityHelper(dayIndex, this.availability[i])
      }
    },
    availabilityHelper(index, availability) {
      const days = ['monAv', 'tueAv', 'wedAv', 'thuAv', 'friAv'];
      const formattedAvailability = this.availabilityTimeFormatter(availability);

      if (this[days[index - 1]] === '-') {
        this[days[index - 1]] = formattedAvailability;
      } else {
        this[days[index - 1]] += "/" + formattedAvailability;
      }
    },
    availabilityTimeFormatter(availability) {
      const fromTime = this.getTimeHelper(availability.from)
      const toTime = this.getTimeHelper(availability.to)

      return (fromTime + "-" + toTime)
    },
    getTimeHelper(dateString) {
      let date = new Date(dateString);
      let hours = date.getHours();
      let minutes = date.getMinutes();

      minutes = minutes < 10 ? "0" + minutes : minutes;

      return hours + ":" + minutes;
    },
    buttonBackPage() {
      this.$router.back();
    },
    toggleSkillList() {
      this.showTempSkills = !this.showTempSkills;

      if (this.showTempSkills === true) {
        this.showSkillsTitle = 'Meer weegeven';
      } else {
        this.showSkillsTitle = 'Minder weegeven';
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
  margin: 1%;
  font-weight: bold;
  font-size: 14px;
}

</style>