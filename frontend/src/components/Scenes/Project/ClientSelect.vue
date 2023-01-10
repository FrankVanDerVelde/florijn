<template>
  <Listbox as="div" @update:modelValue="update($event)" :value="value">
    <div class="relative mt-1">
      <ListboxButton
          class="relative w-full cursor-default rounded-md border border-neutral-200 bg-white py-2 pl-3 pr-10 text-left shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500 sm:text-sm">
        <span class="flex items-center">
          <Asset v-if="value != null"
               :src="value.avatarUrl ?? 'https://icons.veryicon.com/png/o/miscellaneous/bottom-navigation-bar/home-page-not-selected.png'"
               class="h-6 w-6 flex-shrink-0 rounded-full"/>
          <span class="ml-3 block truncate" :class="{'opacity-50 font-medium': value.name == null}">{{ value.name ?? "Selecteer een klant" }}</span>
        </span>
        <span class="pointer-events-none absolute inset-y-0 right-0 ml-3 flex items-center pr-2">
          <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true"/>
        </span>
      </ListboxButton>

      <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100" leave-to-class="opacity-0">
        <ListboxOptions
            class="absolute z-10 mt-1 max-h-56 w-full overflow-auto rounded-md bg-neutral-0 py-1 text-base shadow-lg ring-1 ring-neutral-200 focus:outline-none sm:text-sm">
          <ListboxOption as="template"
                         v-for="client in clients"
                         :key="client.id"
                         :value="client"
                         v-slot="{ active, selected }">
            <li :class="[selected ? 'bg-opacity-50 bg-neutral-100' : active ? 'bg-neutral-100' : 'text-neutral-900', 'relative select-none py-2 pl-3 pr-9 cursor-pointer']">
              <div class="flex items-center">
                <Asset :src="client.avatarUrl" alt="" class="h-6 w-6 flex-shrink-0 rounded-full"/>
                <span :class="[selected ? 'font-semibold' : 'font-normal', 'ml-3 block truncate']">{{ client.name}}</span>
              </div>

              <span v-if="selected" :class="[active ? 'text-white' : 'text-indigo-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
                  <CheckIcon class="h-5 w-5" aria-hidden="true"/>
              </span>
            </li>
          </ListboxOption>
        </ListboxOptions>
      </transition>
    </div>
  </Listbox>
</template>

<script>

import {Listbox, ListboxButton, ListboxOption, ListboxOptions} from '@headlessui/vue'
import {CheckIcon, ChevronUpDownIcon} from '@heroicons/vue/20/solid'
import Asset from "../../Common/Asset.vue";

export default {
  name: "ClientSelect",
  components: {Asset, ListboxButton, ListboxOption, ListboxOptions, Listbox, ChevronUpDownIcon, CheckIcon},
  emits: ['update:modelValue'],

  data() {
    return {
      value: this.modelValue,
    }
  },

  methods: {
    update(value) {
      this.value = value;
      this.$emit('update:modelValue', value);
    }
  },

  props: {
    clients: {
      type: Array,
      required: true,
    },
    selected: {
      type: Object,
      required: false,
      default: null
    },
    modelValue: {
      type: Object
    }
  }

}
</script>