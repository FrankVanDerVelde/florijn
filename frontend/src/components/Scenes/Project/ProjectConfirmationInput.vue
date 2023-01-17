<template>
  <div class="mt-3">
    <label class="block text-base leading-5 text-gray-600 font-bold mt-3">Bevestiging</label>
    <p class="text-sm text-gray-600 block mb-1">Typ de project titel in het invoerveld om deze actie te bevestigen: "<kbd>{{ needed }}</kbd>"</p>
    <input id="confirm-input" :placeholder="needed" :value="modelValue" @input="$emit('update:modelValue', $event.target.value)"/>
  </div>
</template>

<script>
export default {
  name: "ProjectConfirmationInput",
  emits: ['update:modelValue'],

  props: {
    needed: {
      type: String,
      required: true
    },
    modelValue: {
      type: String
    }
  }
}
</script>

<style scoped>
input {
  width: 100%;
  border-color: rgb(209, 213, 219);
  --tw-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
  --tw-shadow-colored: 0 1px 2px 0 0 0 #0000;
  box-shadow: var(--tw-ring-offset-shadow, 0 0 #0000), 0 0 #0000, var(--tw-shadow);
  border-radius: 0.375rem;
  margin-top: .25rem;
  background: #fff;
  border-width: 1px;
  padding: 0.5rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5rem;
  font-weight: 400;
}
</style>