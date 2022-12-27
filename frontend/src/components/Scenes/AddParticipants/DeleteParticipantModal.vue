<template>
  <Dialog :open="true" @click="close" class="relative z-50">
    <div class="fixed inset-0 bg-black/30" aria-hidden="true"/>

    <div class="fixed inset-0 flex items-center justify-center p-4">

      <DialogPanel class="w-full max-w-sm rounded bg-white p-3">
        <DialogTitle class="text-2xl font-bold text-gray-600 text-center mt-3 mb-2">Verwijder deelnemer</DialogTitle>
        <DialogDescription class="text-center font-medium text-gray-500 mb-4">
          Weet je zeker dat je {{fullUserName}} wilt verwijderen?
        </DialogDescription>

        <div
            class="px-4 flex flex-row py-4 min-w-min border-l-4 border-app_red-400 bg-app_red-100 rounded mx-auto">
          <div>
            <h2 class="text-lg font-bold text-app_red-500">Waarchuwing</h2>
            <p class="text-sm my-2 text-app_red-500 font-medium">De verwijderde deelnemer zal geen toegang meer hebben
              tot de data van het project.</p>
          </div>
        </div>
        <div class="flex mt-5 mb-3">
          <button class="button mx-2 bg-gray-500" @click="close">Cancel</button>
          <button class="button mx-2 bg-app_red-300" @click="del">Verwijder</button>
        </div>
      </DialogPanel>
    </div>
  </Dialog>
</template>

<script>
import {Dialog, DialogDescription, DialogPanel, DialogTitle} from "@headlessui/vue";

export default {
  components: {DialogDescription, DialogTitle, DialogPanel, Dialog},

  props: {
    participant: {
      type: Object,
      required: true
    }
  },

  computed: {
    fullUserName() {
      return this.participant.participant.user.firstName + " " + this.participant.participant.user.lastName;
    }
  },

  methods: {
    close() {
      this.$emit('close');
    },
    del() {
      this.$emit('delete', {participant: this.participant});
    }
  }

}
</script>

<style scoped>
button {
  width: 100%;
  font-size: 16px;
  font-weight: 500;
  padding: 10px 15px;
  border-radius: 8px;
  color: white;
}

</style>