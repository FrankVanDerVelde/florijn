<template >
  <div class="flex flex-col h-fit mt-4 ml-2 p-3 fa-border rounded-xl ">
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
            <li class="accent-neutral-700" v-for="skills in skill[0].skill.slice(0, 3)" :key="skills.id">{{skills.name }}
            </li>
          </ul>
        </div>
      </div>

      <div class="flex m-0 ml-2 p-0 relative">
        <div class="add-button hover:border-primary-500 cursor-pointer">
          <font-awesome-icon icon="plus" @click.once="inputFields = !inputFields"/>
        </div>
        <div class="profile-button hover:border-neutral-100 hover:bg-neutral-50 cursor-pointer">
          <font-awesome-icon icon="user"/>
        </div>
        <div class="bottom-0 right-0 ml-3.5 self-end m-0 p-0">
          <div class="star-color" v-for="skills in skill[0].skill.slice(0,3)" :key="skills">
            <font-awesome-icon v-for="rating in skills.rating" :key="rating" icon="star"/>
          </div>
        </div>
      </div>


    </div>
    <div v-if="inputFields" class="mt-2">
      <div class=" flex flex-col my-2">
        <input v-model="roleInput" type="text" id="roleButton"
               class="bg-neutral-50 border border-neutral-200 text-neutral-900 text-sm rounded-lg focus:ring-primary-300 focus:border-blue-500 block w-full p-2.5"
               :placeholder="rolePlaceholder" required>

      </div>
      <div>
        <input v-model="hourInput" type="number" id="hourButton"
               class="bg-neutral-50 border border-neutral-200 text-neutral-900 text-sm rounded-lg focus:ring-primary-300 focus:border-blue-500 block w-full p-2.5"
               :placeholder="hourPlaceholder" required>
      </div>

      <div class="mt-4">
        <button class="accept cursor-pointer"
                @click="$emit('addParticipant', {role: roleInput, hourlyRate: hourInput, participant: participant})">
          Toevoegen
        </button>
      </div>
      <div v-if="validation" class="fa-border text-center validation">
        Voer alle velden in
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
      inputFields: false,
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

.validation {
  color: red
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

button.accept {
  background-color: var(--app_green-400);
  color: black;
}

</style>