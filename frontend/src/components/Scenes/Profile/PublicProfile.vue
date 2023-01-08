<template>
  <div class="page-main-mw px-20 pt-[4em]">
    <div class="container flex flex-col">
      <div class="py-0.5">
        <p class="back-text" @click="buttonBackPage()">&lt Terug</p>
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
                  <div>
                    <button v-if="this.user.role === 'ADMIN'" class="bg-primary-500 border-[1px] h-[38px] w-[180px] text-sm rounded-md
                    mr-2 text-neutral-0">Op project zetten
                    </button>
                    <button class="bg-neutral-50 border-neutral-200 mt-2 border-[1px] justify-center
                    rounded-md bold p-2 mr-2 h-[38px] w-[180px] flex text-neutral-900">CV downloaden
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box flex-col p-2 w-full mt-3">
            <div>
              <div class="back-text float-right" @click="toggleSkillList()">{{ showSkillsTitle }}</div>
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
            <div class="my-2" v-if="this.availability.length === 0">Er is geen beschikbaarheid gevonden voor
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
  </div>
</template>

<script>

import ProjectListDetailsSummary from "../../Scenes/Project/ProjectsListDetailsSummary.vue";
import SkillListSummary from "../../Scenes/Project/SkillListSummary.vue";
import Asset from "../../Common/Asset.vue";

export default {
  name: "PublicProfile",
  inject: ['dateService', 'userRepository', 'projectRepository', 'skillsRepository', 'memoryAvailabilityRepository'],
  components: {ProjectListDetailsSummary, SkillListSummary, Asset},
  computed: {
    user() {
      return JSON.parse(localStorage.getItem('user')) ?? {};
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
      this.projects = await this.projectRepository.fetchProjects("UNARCHIVED");

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
    async fetchAvailability() {
      const weekNumber = this.dateService.currentWeekOfYear();
      this.availability = await this.memoryAvailabilityRepository.fetchAvailabilityForUserInWeek(this.specialistId, weekNumber)

      for (let i = 0; i < this.availability.length; i++) {
        const dayIndex = this.dateService.dayIndex(this.availability[0].date)
        this.availabilityHelper(dayIndex, this.availability[0])
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