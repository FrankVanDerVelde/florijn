<template>
  <div class="flex flex-row mt-4 p-3 md:w-auto fa-border ">
    <div class="flex">

      <div>
        <img :src="participant.avatar" alt="participant avatar" class="icon-container">
      </div>

      <div class="ml-3">
        <div>
          <div class="font-bold">{{ name }}</div>
          <div class="font-medium text-neutral-500">{{ participant.role }}</div>
        </div>
        <div>

          <ul>
            <li v-for="skill in skills.slice(0, 3)" :key="skill.id">{{ skill.name }}</li>

          </ul>


        </div>
      </div>

      <div class="fa-border bottom-0 right-0 ">

        <div class="flex" v-for="skill in skills.slice(0,3)" :key="skill">
          <div v-for="level in skill.level" :key="level">
            <font-awesome-icon icon="star" class="star-color"/>
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
      required: true
    }
  },

  data() {
    return {
      skills: [{
        id: 0,
        name: "Microsoft Access",
        level: 3
      }, {
        id: 1,
        name: "Microsoft Access VBA",
        level: 2
      }, {
        id: 2,
        name: "Microsoft Excel",
        level: 4
      }, {
        id: 3,
        name: "Microsoft Access",
        level: 1
      }, {
        id: 4,
        name: "Microsoft Access",
        level: 4
      }
      ]
    }
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