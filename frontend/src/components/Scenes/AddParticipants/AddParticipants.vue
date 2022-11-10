<template>

  <div class="page-main-mw p-4 fa-border">

    <div class="flex ">
      <div class="mb-3 w-1/5 fa-border">
        <ul>
          <li>html</li>
          <li>css</li>
          <li>javascript</li>
        </ul>
      </div>

      <div class="flex flex-row flex-wrap justify-evenly ">

        <ParticipantCard v-for="participants in participants" :key="participants.id" :participant="participants"></ParticipantCard>

      </div>

    </div>
  </div>

</template>

<script>
import ParticipantCard from "./ParticipantCard.vue";

export default {
  name: "AddParticipants",
  components: {ParticipantCard},
  data() {
    return {
      participants: [
        {
          id: 0,
          firstName: "Andrew",
          lastName: "Alfred",
          role: "Product Owner",
          avatar: "/src/assets/avatars/avatar1.avif",
          email: "andrewa@florijn.com"
        }, {
          id: 1,
          firstName: "Withney",
          lastName: "Keulen",
          role: "Lead Developer",
          avatar: "/src/assets/avatars/avatar2.avif",
          email: "withneyk@florijn.com"
        }, {
          id: 2,
          firstName: "Jan",
          lastName: "Timmermans",
          role: "Designer",
          avatar: "/src/assets/avatars/avatar3.avif",
          email: "jant@florijn.com"
        }
      ]

    }
  }
}


</script>

<style scoped>

</style>