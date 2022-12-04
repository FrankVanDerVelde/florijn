<template>
  <div class="page-main-mw px-20 pt-[4em]">
    <div class="container flex flex-col">
      <div class="py-0.5">
        <p class="back-text" @click="buttonBackPage()">&lt Terug</p>
      </div>
      <div class="flex grid grid-cols-16">
        <div class="flex flex-col row-start-1 col-span-7 p-2 w-full">
          <div class="box flex  p-2 justify-center h-[120px]">
            <img :src="this.userData.avatarUrl" alt="profile picture" class="w-[82px] h-[82px] flex rounded-full mr-4">
            <div class="flex flex-col justify-between container ml-2">
              <div class="flex flex-col mb-3">
                <div class="container flex justify-between m-1">
                  <div class="relative bottom-0">
                    <div class="font-bold flex">{{ userData.firstName + " " + userData.lastName }}</div>
                    <div class="flex font-semibold text-neutral-500">Developer</div>
                    <div class="flex mt-2">{{ userData.email }}</div>
                  </div>
                  <div>
                    <button class="bg-primary-500 border-[1px] h-[38px] w-[180px] text-sm rounded-md
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
              <div>{{noSkillsFoundText}}</div>
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
            <div class="my-2">{{ noProjectsFoundText }}</div>
          </div>
          <div class="box flex flex-col mt-3 p-2">
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
  </div>
</template>

<script>

import ProjectListDetailsSummary from "../../Scenes/Project/ProjectsListDetailsSummary.vue";
import SkillListSummary from "../../Scenes/Project/SkillListSummary.vue";

export default {
  name: "PublicProfile",
  inject: ['fetchService'],
  components: {ProjectListDetailsSummary, SkillListSummary},
  data() {
    return {
      specialistId: '',
      userData: '',
      projects: [],
      skills: [],
      tempSkills: [],
      showTempSkills: true,
      showSkillsTitle: 'Meer weergeven',
      noProjectsFoundText: '',
      noSkillsFoundText: ''
    }
  },
  created() {
    if (this.id === "null" || localStorage.getItem("role") === "SPECIALIST") {
      this.$router.push("/home");
    }
    this.specialistId = this.$route.params.Id;
    this.fetchUserInfo();
    this.fetchProjects();
    this.fetchSkills();
  },
  methods: {
    async fetchUserInfo() {
      this.userData = await this.fetchService.fetchUrl("/users/" + this.specialistId);
    },
    async fetchProjects() {
      this.projects = await this.fetchService.fetchJson("/projects?user=" + this.specialistId);

      if (this.projects == null) {
        this.projects = [];
      }

      if (this.projects.length === 0) {
        this.noProjectsFoundText = "Er zijn geen projecten gevonden voor " + this.userData.firstName;
      }
    },
    async fetchSkills() {
      this.skills = await this.fetchService.fetchJson("/skills/user-skills/" + this.specialistId);
      if (this.skills == null) {
        this.skills = [];
      }

      if (this.skills.length > 8) {
        this.tempSkills = this.skills.slice(0, 8);
      }

      if (this.skills.length === 0) {
        this.noSkillsFoundText = "Er zijn geen skills gevonden " + this.userData.firstName;
      }
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