<template>
  <h2 class="!mb-0 mt-4 header-2" >Huidige deelnemers</h2>


  <div class="flex flex-row flex-wrap gap-8">
    <participant v-for="participant in project.participants" :key="participant.id" :participant="participant" :edit="true"
                 @selectedParticipant="item => selectedDeleteSpecialist = item"/>
  </div>

  <h2 class=" mb-4 mt-10 header-2" >Deelnemers toevoegen</h2>
  <div class="flex flex-row participant-container" v-if="specialists.length !== 0">
    <div class=" w-2/5 filter-container">
      <FilterParticipants v-for="skillset in skillGroup" :key=skillset :skillset=skillset
                          @selectedSkill="skillSelect"/>
    </div>
    <div class="ml-10 p-5 flex flex-row flex-wrap self-start justify-evenly participant-container">
      <ParticipantCard v-for="participants in filteredParticipantsList" :key=participants.id :skill=skills
                       :participant=participants @addParticipant="item => selectedSpecialist = item" :validation="validation"/>
    </div>
  </div>

  <!--Modals for adding and deleting specialists-->
  <AddParticipantModal v-if="this.selectedSpecialist != null" :participant="selectedSpecialist" :project="this.project"
                       @accept="addParticipant" @reject="this.selectedSpecialist = null"/>
  <DeleteParticipantModal v-if="selectedDeleteSpecialist != null" @close="selectedDeleteSpecialist = null"
                          @delete="deleteParticipant" :participant="selectedDeleteSpecialist"/>

</template>

<script>
import ParticipantCard from "./ParticipantCard.vue";
import FilterParticipants from "./FilterParticipants.vue";
import Participant from "../Project/Participant.vue";
import AddParticipantModal from "./AddParticipantModal.vue";
import DeleteParticipantModal from "./DeleteParticipantModal.vue";

export default {
  name: "AddParticipants",
  components: {DeleteParticipantModal, AddParticipantModal, Participant, ParticipantCard, FilterParticipants},
  inject: ['projectFetchService', 'fetchService', 'skillFetchService'],

  props: {
    project: {
      type: Object,
      required: true
    }
  },


  methods: {
    addParticipant(specialist) {
      this.selectedSpecialist = null;

      if (specialist.role === "" || specialist.hourlyRate === "") {
        console.log("role or hourlyRate is null");
        this.validation = true;
        return;
      }
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
      this.filteredParticipantsList = this.specialists.filter(specialist => !this.project.participants.some(participant => participant.user.id === specialist.id))
      this.validation = false;

    },

    skillSelect(selectedSkill) {
      if (!selectedSkill.checked) {
        this.selectedFilters.push(selectedSkill.selectedSkill)
      } else {
        this.selectedFilters = this.selectedFilters.filter(selectedfilters => selectedfilters.id !== selectedSkill.selectedSkill.id)
      }

      //if selectedFilters array is empty, show all specialists
      if (this.selectedFilters.length === 0) {
        this.filteredParticipantsList = this.specialists;
        return
      }

      this.filteredParticipantsList = this.specialists.filter(specialist => specialist.skills.some(skill =>
          this.selectedFilters.some(selectedfilter => selectedfilter.id === skill.id && skill.rating > 3)))
    },
    deleteParticipant(selectedParticipant) {
      console.log(selectedParticipant)
      this.selectedDeleteSpecialist = null;
      const participant = selectedParticipant.participant.participant;
      this.project.participants = this.project.participants.filter(projectParticipant => projectParticipant.user.id !== participant.user.id)
      this.projectFetchService.fetchJsonMethod(`/${this.$route.params.projectId}/participants/${participant.user.id}/delete`, "DELETE")
      this.filteredParticipantsList = this.specialists.filter(specialist => !this.project.participants.some(participant => participant.user.id === specialist.user.id))
    }


  },


  async created() {
    this.project = await this.projectFetchService.fetchJson(`/${this.$route.params.projectId}`);
    this.specialists = await this.fetchService.fetchJson("/users/role/specialist")
    this.specialists = this.specialists.filter(specialist => !this.project.participants.some(participant => participant.user.id === specialist.id))
    this.skillGroup = await this.skillFetchService.fetchJson("/groups")
    this.filteredParticipantsList = this.specialists;

    this.skillGroup.forEach(skill => skill.skills.forEach(skill => skill.checked = false))

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
      skillset: {},
      selectedFilters: [],
      filteredParticipantsList: {},
      validation: false,
      selectedSpecialist : null,
      selectedDeleteSpecialist: null,

      skillGroup: {},

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