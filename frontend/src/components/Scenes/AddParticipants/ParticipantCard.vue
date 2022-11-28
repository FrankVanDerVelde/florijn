<template>
  <div class="flex flex-col h-fit mt-4 ml-5 p-3 fa-border rounded-xl" @click.once="inputFields = !inputFields">
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
            <li class="accent-neutral-700" v-for="skills in skill[0].skill.slice(0, 3)" :key="skills.id">{{
                skills.name
              }}
            </li>
          </ul>
        </div>
      </div>

      <div class="flex m-0 p-0">
        <div class="bottom-0 right-0 ml-3.5 self-end m-0 p-0">
          <div class=" " v-for="skills in skill[0].skill.slice(0,3)" :key="skills">
            <font-awesome-icon v-for="rating in skills.rating" :key="rating" icon="star" class="star-color"/>
          </div>
        </div>
      </div>

    </div>
    <div v-if="inputFields" class="mt-2">
      <div class=" flex flex-col my-2">
        <input v-model="roleInput" type="text" id="roleButton"
               class="bg-neutral-50 border border-neutral-200 text-neutral-900 text-sm rounded-lg focus:ring-primary-300 focus:border-blue-500 block w-full p-2.5"
               :placeholder="rolePlaceholder" @change="deleteValidationText" required>

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
export default {
  name: "ParticipantCard",
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