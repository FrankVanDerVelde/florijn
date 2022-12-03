<template>
  <TransitionRoot appear :show="isOpen" as="template">
    <Dialog as="div" @close="$emit('close')" class="relative z-10">
      <TransitionChild
          as="template"
          enter="duration-300 ease-out"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="duration-200 ease-in"
          leave-from="opacity-100"
          leave-to="opacity-0">
        <div class="fixed inset-0 bg-neutral-900 bg-opacity-25"/>
      </TransitionChild>

      <div class="fixed inset-0 overflow-y-auto">
        <div class="flex min-h-full items-center justify-center p-4 text-center">
          <TransitionChild
              as="template"
              enter="duration-300 ease-out"
              enter-from="opacity-0 scale-95"
              enter-to="opacity-100 scale-100"
              leave="duration-200 ease-in"
              leave-from="opacity-100 scale-100"
              leave-to="opacity-0 scale-95">
            <DialogPanel
                class="w-full max-w-md transform rounded-2xl bg-neutral-0 text-left align-middle shadow-xl transition-all">
              <div class="p-6">
                <DialogTitle
                    as="h3"
                    class="text-lg font-medium leading-6 text-neutral-900">
                  {{ title }}
                </DialogTitle>
                <div class="mt-2">
                  <slot/>
                </div>
              </div>

              <div v-if="buttons" class="bg-gray-50 px-4 py-3 flex flex-col sm:flex-row justify-end gap-2 sm:px-6 rounded-b-2xl">
                <button v-for="(val, prop) in buttons" :key="prop" type="button" @click="onButtonClick(val, prop)"
                        :class="['inline-flex w-full justify-center rounded-md border px-4 py-2 text-base font-medium shadow-sm focus:outline-none focus:ring-2 focus:ring-offset-2 sm:w-auto sm:text-sm',
                val.type == 'secondary' ? 'border-gray-300 bg-white text-gray-700 hover:bg-gray-50 focus:ring-app_indigo-500' : val.type == 'danger' ? 'border-transparent bg-app_red-600 text-white hover:bg-app_red-700 focus:ring-app_red-500' : '']">
                  {{ val.title }}
                </button>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script>

import {Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot} from '@headlessui/vue'

export default {
  name: "Modal",
  components: {Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot},
  emits: ['close'],

  props: {
    title: {
      type: String,
      required: true
    },
    isOpen: {
      required: false,
      default: false,
    },
    buttons: {
      type: Object,
      default: function () {
        return {
          cancel: {
            type: 'secondary',
            role: 'cancel',
            title: 'Cancel'
          },
          submit: {
            type: 'danger',
            title: 'Overdragen'
          },
        }
      }
    },
  },
  methods: {
    onButtonClick(val, prop) {
      this.$emit(`click:${prop}`);
      if (val.role == "cancel") {
        this.$emit('close');
      }
    }
  }
}
</script>

<style scoped>

</style>