<template>
  <ProjectParticipantList :participants=project.participants class="cursor-pointer" ></ProjectParticipantList>

  <h2 class=" mb-4 mt-10 header-2">Deelnemers toevoegen</h2>
  <div class="flex flex-row ">
    <div class=" w-1/5 ">
      <FilterParticipants v-for="(skillset, index) in skills" :index="index" :skillset="skillset" :key="skillset"/>
    </div>
    <div class="ml-10 p-5 flex flex-row flex-wrap self-start justify-evenly ">
      <ParticipantCard class="cursor-pointer"  v-for="participants in specialists"  :key="participants.id" :skill="skills"
                       :participant="participants" @click="addParticipant(participants)"/>

    </div>
  </div>

</template>

<script>
import ParticipantCard from "./ParticipantCard.vue";
import FilterParticipants from "./FilterParticipants.vue";
import ProjectParticipantList from "../Project/ProjectParticipantList.vue";

export default {
  name: "AddParticipants",
  components: {ProjectParticipantList, ParticipantCard, FilterParticipants},
  inject: ['projectFetchService', 'specialistFetchService'],

  props: {
    projectId: {
      type: Number,
      required: true
    }
  },

  methods : {
    addParticipant(specialist) {

      specialist.role = "Developer"
      specialist.hourlyRate = 40
      specialist.userId = specialist.id

      this.projectFetchService.fetchJsonPost(`/${this.$route.params.projectId}/participants/add`, specialist)
      this.project.participants.push(specialist)
    }
  },


  async created() {
    this.project = await this.projectFetchService.fetchJson(`/${this.$route.params.projectId}`);
    this.specialists = await this.specialistFetchService.fetchJson("")

    // when a non-existing project is requested, redirect to the /projects page.
    if (this.project == null) {
      this.$router.push({name: 'projects'});
      return;
    }
  },


  data() {
    return {
      project:{},
      specialists: {},
      skills: [{
        name: "Microsoft Office Front-end",
        skill: [{
          id: 0,
          name: "Microsoft Access",
          rating: 3
        }, {
          id: 1,
          name: "Microsoft Access VBA",
          rating: 2
        }, {
          id: 2,
          name: "Microsoft Excel",
          rating: 1
        }, {
          id: 3,
          name: "Microsoft Access",
          rating: 1
        }, {
          id: 4,
          name: "Microsoft Access",
          rating: 4
        }
        ]
      }, {
        name: "Back-end",
        skill: [{
          id: 0,
          name: "MS SQL Server",
          Rating: 3
        }, {
          id: 1,
          name: "MS SQL-Server Stored Procedures",
          Rating: 2
        }, {
          id: 2,
          name: "MS Office Excel",
          Rating: 5
        }, {
          id: 3,
          name: "Ms office Eccel pt 2",
          rating: 3
        },]
      }]
    }
  }
}


</script>

<style scoped>

</style>