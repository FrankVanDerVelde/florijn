<template>
  <div class="modal-container flex fixed w-full h-full top-0 left-0 z-50 justify-center items-center">
    <div class="modal flex flex-col relative bg-neutral-0">
      <div>
        <h2>Deelnemer toevoegen</h2>
        <div class="subheader">
          {{ fullName }} toevoegen aan project {{ project.title }}
        </div>
        <div class="mx-7">

          <div>
            <div class="mt-10 font-semibold text-lg">Rol van de deelnemer</div>
            <div class="font-medium text-sm text-neutral-400 mb-2">Voer in welke rol {{ fullName }} in het project gaat
              vervullen
            </div>
            <input v-model="roleInput" type="text" id="roleButton"
                   class="bg-neutral-25 border border-neutral-200 text-neutral-900 text-sm rounded-sm focus:ring-primary-300 focus:border-blue-500 block w-full p-2.5"
                   :placeholder="rolePlaceHolder" @change="roleValidation = false" required>
            <div v-if="roleValidation">Voer een rol in</div>

          </div>

          <div>
            <div class="mt-6 font-semibold text-lg">Aantal uren van de deelnemer</div>
            <div class="font-medium text-sm text-neutral-400 mb-2">Voer het aantal uren in dat {{ fullName }} aan dit
              project gaat werken
            </div>
            <input v-model="hourRateInput" type="number" id="hourRateInput"
                   class="bg-neutral-25 border border-neutral-200 text-neutral-900 text-sm rounded-sm focus:ring-primary-300 focus:border-blue-500 block w-full p-2.5"
                   :placeholder="hourRatePlaceHolder" @change="hourRateValidation = false" required>
            <div v-if="hourRateValidation">Voer het aantal uur in</div>
          </div>
          <div class="flex my-10">
            <button class="reject mx-2" @click="$emit('reject')">Cancel</button>
            <button class="accept mx-2" @click=addParticipant>Deelnemer toevoegen</button>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>


export default {
  name: "AddParticipantModal",
  components: {},
  emits: ['accept', 'reject'],

  computed: {
    fullName() {
      return this.participant.firstName + " " + this.participant.lastName;
    }
  },

  data() {
    return {
      roleInput: "",
      hourRateInput: "",
      rolePlaceHolder: "Rol van de deelnemer",
      hourRatePlaceHolder: "Aantal uren van de deelnemer",
      roleValidation: false,
      hourRateValidation: false,
    }
  },

  methods: {
    addParticipant() {
      this.roleValidation = false;
      this.hourRateValidation = false;

      if (this.roleInput === "") {
        this.rolePlaceHolder = "Voer een rol in";
        this.roleValidation = true;
      }

      if (this.hourRateInput === "") {
        this.hourRatePlaceHolder = "Voer een aantal uren in";
        this.hourRateValidation = true;
      }

      if (this.hourRateValidation || this.roleValidation) {
        return;
      }

      this.$emit('accept', {"participant" : this.participant, role: this.hourRateInput, hourlyRate: this.hourRateInput});
    },
  },

  props: {
    project: {
      type: Object,
      required: true
    },
    participant: {
      type: Object,
      required: true
    }
  },


}
</script>

<style scoped>

.modal-container {
  background: rgba(0, 0, 0, 0.5);
}

.modal {
  width: 600px;
  visibility: visible;
  max-height: 85%;
  border-radius: 8px;
  margin: 0 auto;
}


button.accept {
  background-color: var(--app_green-400);
}

button.reject {
  background-color: var(--app_red-300);
}

h2 {
  text-align: center;
  margin-top: 60px;
  font-weight: 600;
  font-size: 30px;
  color: #000;
  margin-bottom: .5rem;
  line-height: 1.2;
  font-family: Inter, sans-serif;
}

.subheader {
  font-size: 18px;
  color: #1F2933;
  text-align: center;
  margin-bottom: 0;
  padding: 0 30px;
  font-weight: 500;
  line-height: 1.2;
  font-family: Inter, sans-serif;
}

button {
  width: 100%;
  font-size: 16px;
  font-weight: 500;
  padding: 10px 15px;
  border-radius: 8px;
  color: white;
}


</style>