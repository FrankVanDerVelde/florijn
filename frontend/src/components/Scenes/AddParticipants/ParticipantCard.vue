<template>
  <div class="flex flex-row mt-4 ml-5 p-3 md:w-auto fa-border rounded-xl">
    <div class="flex">


      <div>
        <img :src="participant.avatarUrl" alt="participant avatar" class="icon-container">
      </div>

      <div class="ml-3">
        <div>
          <div class="font-bold">{{ name }}</div>
          <div class="font-medium text-neutral-500">{{ participant.role }}</div>
        </div>
        <div>
          <ul>
            <li class="accent-neutral-700" v-for="skills in skill[0].skill.slice(0, 3)" :key="skills.id">{{ skills.name }}</li>
          </ul>
        </div>
      </div>

      <div class="flex m-0 p-0">
        <div class=" bottom-0 right-0 ml-3.5 self-end m-0 p-0">
          <div class=" " v-for="skills in skill[0].skill.slice(0,3)" :key="skills">
            <font-awesome-icon v-for="rating in skills.rating" :key="rating" icon="star" class="star-color"/>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: "ParticipantCard",
  components: {},


  computed: {
    name() {
      let lastNameParts = this.participant.lastName.split(" ");
      return this.participant.firstName + " " + lastNameParts[lastNameParts.length - 1].charAt(0) + ".";
    }
  },

  props: {
    participant: {
      type: Object,
      required: false
    },
    skill: {
      type: Object,
      required: true
    }
  },

  data() {
    return {}
  }
}
</script>

<style scoped>
.icon-container {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  border-radius: 18px;
}

.star-color {
  color: var(--primary-500)
}
</style>