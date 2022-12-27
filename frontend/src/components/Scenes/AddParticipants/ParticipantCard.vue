<template >
  <div class="flex flex-col mt-4 ml-2 p-3 pr-2 fa-border rounded-xl ">
    <div class="flex">

      <div>
        <img :src="participant.avatarUrl" alt="participant avatar" class="icon-container">
      </div>

      <div class="ml-3">
        <div>
          <div class="font-bold">{{ name }}</div>
          <div class="font-medium text-neutral-500">{{ participant.role }}</div>
          <div v-if="this.skill.length === 0" class="flex flex-row-reverse mt-1">
            <div class="add-participant hover:border-primary-500 cursor-pointer">
              <font-awesome-icon icon="plus" @click="$emit('addParticipant', participant)"/>
            </div>
            <div class="add-project hover:border-neutral-100 hover:bg-neutral-50 cursor-pointer mr-1">
              <font-awesome-icon icon="user"/>
            </div>
          </div>
        </div>
        <div>
          <ul>
            <li class="accent-neutral-700" v-for="skills in skill.slice(0, 5)" :key="skills.id">{{ skills.name }}
            </li>
          </ul>
        </div>
      </div>

      <div class="flex m-0 ml-2 p-0 relative">
        <div v-if="this.skill.length !== 0">
          <div class="add-button hover:border-primary-500 cursor-pointer">
            <font-awesome-icon icon="plus" @click="$emit('addParticipant', participant)"/>
          </div>
          <div class="profile-button hover:border-neutral-100 hover:bg-neutral-50 cursor-pointer">
            <font-awesome-icon icon="user"/>
          </div>
        </div>
        <div class="bottom-0 right-0 ml-3.5 self-end m-0 p-0">
          <div class="star-color" v-for="skills in getSkills.slice(0,5)" :key="skills">
            <font-awesome-icon v-for="rating in skills.rating" :key="rating" icon="star"/>
          </div>
        </div>
      </div>
    </div>

  </div>


</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

export default {
  name: "ParticipantCard",
  components: {FontAwesomeIcon},
  emits: ['addParticipant'],

  computed: {
    name() {
      let lastNameParts = this.participant.lastName.split(" ");
      return this.participant.firstName + " " + lastNameParts[lastNameParts.length - 1].charAt(0) + ".";
    },
    getSkills() {
      return this.participant.skills.filter(skill => this.skill.some(s => s.id === skill.id));
    }
  },
  methods: {}
  ,

  props: {
    participant: {
      type: Object,
      required: false
    },
    skill: {
      type: Object,
      required: true
    },
    validation: {
      type: Boolean,
      required: false
    }
  },

  data() {
    return {
      rolePlaceholder: "Voeg een rol toe",
      hourPlaceholder: "Voeg een hourlyRate toe",
      roleInput: "",
      hourInput: "",
    }
  }
}
</script>

<style scoped>

.add-button {
  position: absolute;
  top: 0;
  right: 0px;
  color: var(--primary-500);
  border: 1px solid var(--neutral-200);
  padding: 2px 6px;
  font-size: 14px;
  border-radius: 6px;
}

.profile-button {
  position: absolute;
  top: 0;
  right: 30px;
  color: var(--neutral-500);
  border: 1px solid var(--neutral-200);
  padding: 2px 6px;
  font-size: 14px;
  border-radius: 6px;
}

.add-participant {
  color: var(--primary-500);
  border: 1px solid var(--neutral-200);
  padding: 2px 6px;
  font-size: 14px;
  border-radius: 6px;
}

.add-project {
  color: var(--neutral-500);
  border: 1px solid var(--neutral-200);
  padding: 2px 6px;
  font-size: 14px;
  border-radius: 6px;
}


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

button {
  width: 100%;
  padding: 10px 15px;
  font-size: 16px;
  font-weight: 500;
  font-family: Inter, sans-serif;
  border-radius: 8px;
  transition: all .3s;
}


</style>