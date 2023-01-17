<template>

<!--  <router-link :to="{name: 'project-overview'}"-->
<!--               class="muted error mt-15 block mb-2">-->
<!--    &#60; Terug naar project overzicht-->
<!--  </router-link>-->


  <h2 class="!mb-0 mt-4 header-2" >Huidige deelnemers</h2>

  <div class="flex flex-row flex-wrap gap-8">
    <ProjectParticipant v-for="participant in project.participants" :key="participant.id" :participant="participant" :edit="true"
                 @selectedParticipant="item => selectedDeleteSpecialist = item"/>
  </div>

  <h2 class=" mb-4 mt-10 header-2" >Deelnemers toevoegen</h2>
  <div class="flex flex-row participant-container" v-if="specialists.length !== 0">
    <div class=" min-w-1/6  filter-container">
      <FilterParticipants v-for="skillset in skillGroup" :key=skillset :skillset=skillset
                          @selectedSkill="skillSelect"/>
    </div>

    <div v-if="Object.keys(this.filteredParticipantsList).length === 0" class="muted error m-auto">
      Geen deelnemers gevonden met de huidige zoekopdracht
    </div>

    <div class="ml-10 p-5 m-0 grid grid-cols-3 participant-grid" v-if="selectedFilters.length === 0">
      <ParticipantCard v-for="participants in filteredParticipantsList" :key=participants.id :skill=selectedFilters
                       :participant=participants @addParticipant="item => selectedSpecialist = item" :validation="validation"/>
    </div>

    <div class="ml-10 p-5 m-0 grid grid-cols-2 participant-grid" v-else>
      <ParticipantCard v-for="participants in filteredParticipantsList" :key=participants.id :skill=selectedFilters
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
import ProjectParticipant from "./ProjectParticipant.vue";

export default {
  name: "AddParticipants",
  components: {
    ProjectParticipant,
    DeleteParticipantModal, AddParticipantModal, ParticipantCard, FilterParticipants},
  inject: ['skillsRepository', 'projectRepository', 'userRepository'],

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
        // console.log("role or hourlyRate is null");
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
      this.projectRepository.addParticipant(`${this.$route.params.projectId}`, specialist)
      this.filteredParticipantsList = this.specialists.filter(specialist => !this.project.participants.some(participant => participant.user.id === specialist.id))
      this.validation = false;

    },

    async skillSelect(selectedSkill) {
      if (!selectedSkill.checked) {
        this.selectedFilters.push(selectedSkill.selectedSkill.id)
      } else {
        this.selectedFilters = this.selectedFilters.filter(skill => skill !== selectedSkill.selectedSkill.id)
      }

      this.filteredParticipantsList = null;

      // if selectedFilters array is empty, show all specialists
      if (this.selectedFilters.length === 0) {
        this.filteredParticipantsList = this.specialists;
        return
      }

      this.filteredParticipantsList = await this.userRepository.fetchUsersBySkills( this.selectedFilters)
      this.filteredParticipantsList = this.filteredParticipantsList.filter(specialist => !this.project.participants.some(participant => participant.user.id === specialist.id))

    },

    deleteParticipant(selectedParticipant) {
      this.selectedDeleteSpecialist = null;
      const participant = selectedParticipant.participant.participant;
      this.project.participants = this.project.participants.filter(projectParticipant => projectParticipant.user.id !== participant.user.id)
      this.projectRepository.deleteParticipant(`${this.$route.params.projectId}`, `${participant.user.id}`)
      this.skillSelect()
    }


  },


  async created() {
    try {

      this.project = await this.projectRepository.fetchProjectById(`/${this.$route.params.projectId}`);
      this.specialists = await this.userRepository.fetchUsers("SPECIALIST")
      this.specialists = this.specialists.filter(specialist => !this.project.participants.some(participant => participant.user.id === specialist.id))

      this.skillGroup = await this.skillsRepository.fetchSkillGroups()
      this.filteredParticipantsList = this.specialists;
      this.skillGroup.forEach(skill => skill.skills.forEach(skill => skill.checked = false))

      // when a non-existing project is requested, redirect to the /projects page.
      if (this.project == null) {
        this.$router.push({name: 'projects'});
      }
    } catch {
    }
  },


  data() {
    return {
      specialists: {},
      skillset: {},
      selectedFilters: [],
      filteredParticipantsList: {},
      validation: false,
      selectedSpecialist: null,
      selectedDeleteSpecialist: null,
      skillGroup: {},
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

  .participant-grid {
    grid-template-columns: 1fr;
  }
}

@media screen and (max-width: 1080px) {
  .participant-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}



.muted.error {
  margin-top: 4px;
  color: var(--app_red-400)
}

.participant-grid {
  grid-auto-rows: minmax(min-content, max-content);
}
</style>