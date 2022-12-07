<template>
  <Dialog :open="true" @click="close" class="relative z-50">
    <div class="fixed inset-0 bg-black/30" aria-hidden="true"/>

    <div class="fixed inset-0 flex items-center justify-center p-4">

      <DialogPanel class="w-full max-w-sm rounded bg-white p-3">
        <DialogTitle class="text-xl font-medium leading-6">Verwijder deelnemer</DialogTitle>
        <DialogDescription class="text-l">
          Weet je zeker dat je {{participant.name}} wilt verwijderen?
        </DialogDescription>
        <div class="flex my-10">
          <button class="button accept mx-2" @click="close">Cancel</button>
          <button class="button reject mx-2" @click="del">Verwijder</button>
        </div>
        <!-- ... -->
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

button.accept {
  background-color: var(--app_green-400);
}

button.reject {
  background-color: var(--app_red-300);
}
</style>