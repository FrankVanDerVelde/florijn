<template>
  <ProjectParticipantList :participants=project.participants></ProjectParticipantList>

  <h2 class=" mb-4 mt-10 header-2">Deelnemers toevoegen</h2>
  <div class="flex flex-row participant-container">
    <div class=" w-2/5 filter-container">
      <FilterParticipants v-for="(skillset, index) in skills" :index="index" :skillset="skillset" :key="skillset"/>
    </div>
    <div class="ml-10 p-5 flex flex-row flex-wrap self-start justify-evenly participant-container">
      <ParticipantCard class="cursor-pointer flex-grow-0" v-for="participants in specialists" :key="participants.id" :skill="skills"
                       :participant="participants" @addParticipant="addParticipant" />

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
      type: String,
      required: true
    }
  },


  methods: {
    addParticipant(specialist ) {

      console.log(this.project.participants)

      specialist.userId = specialist.participant.id

     this.project.participants.push({
        role: specialist.role, hourlyRate: specialist.hourlyRate, user:
            {
              id: specialist.participant.id,
              email: specialist.participant.email,
              avatarUrl: specialist.participant.avatarUrl,
              password: specialist.participant.password,
              firstName: specialist.participant.firstName,
              lastName: specialist.participant.lastName
            }
      })
      this.projectFetchService.fetchJsonPost(`/${this.$route.params.projectId}/participants/add`, specialist)

    }
  },


  async created() {
    this.project = await this.projectFetchService.fetchJson(`/${this.$route.params.projectId}`);
    this.specialists = await this.specialistFetchService.fetchJson("")

    // when a non-existing project is requested, redirect to the /projects page.
    if (this.project == null) {
      this.$router.push({name: 'projects'});
    }
  },


  data() {
    return {
      project: {},
      specialists: {},
      assignedSpecialists: {},

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
@media screen and (max-width: 768px) {
  .participant-container {
    flex-direction: column;
  }

  .filter-container {
    width: 100%;
  }

  .participant-container {
    margin: 0;
  }

}
</style>