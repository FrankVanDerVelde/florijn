<template>

  <section class="pt-[24px]" >
    <div class="flex items-center justify-between mb-2">
      <h2 class="!mb-0 header-2">Deelnemers</h2>

      <router-link
          v-if="editButton"
          :to="{name: 'project-participants'}"
          class="bg-primary-400 rounded-md bold p-2 h-[32px] flex items-center text-neutral-0">
        Wijzigen
      </router-link>
    </div>

    <div class="flex flex-row flex-wrap gap-8">
      <participant v-if="this.client != null" :participant="clientInfo"/>

      <participant v-for="participant in participants" :key="participant.id" :participant="participant"/>
    </div>
  </section>

</template>

<script>
import Participant from "./Participant.vue";

export default {
  name: "ProjectParticipantList",
  components: {Participant},

  computed: {
    clientInfo() {
      return {
        user: this.client,
        role: "Project Owner"
      }
    }
  },

  props: {
    participants: {
      type: Array,
      required: true
    },
    editButton: {
      type: Boolean,
      default: false,
    },
    client: {
      type: Object,
      default: null
    }
  }
}
</script>

<style scoped>

</style>