<template>
  <div>
    <input v-model="userInput" placeholder="Enter text">
    <button @click="displayInput">Display</button>
    <div v-html="dangerousHTML"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInput: '',
      dangerousHTML: ''
    }
  },
  methods: {
    displayInput() {
      this.dangerousHTML = this.userInput;
    }
  }
}
</script>