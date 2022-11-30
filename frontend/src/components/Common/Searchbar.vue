<template>
  <div class="searchbar-container relative flex flex-row">
    <div class="relative w-full">
      <input class="searchbar w-full"
             type="text"
             :placeholder="placeholder"
             :value="input"
             @change="$emit('submit', input)"
             @input="updateInput($event.target.value)"
      >
      <div class="searchbar-clear absolute cursor-pointer"
           @click="clearInput">
        <font-awesome-icon icon="circle-xmark"/>
      </div>
    </div>
    <div class="searchbar-enter cursor-pointer flex items-center justify-center"
         @click="$emit('submit', input)">
      <font-awesome-icon icon="magnifying-glass"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "Searchbar",
  emits: ['update:modelValue', 'input', 'submit'],

  data() {
    return {
      input: this.modelValue,
    }
  },

  props: {
    placeholder: {
      type: String,
      required: false,
      default: "Zoeken..."
    },
    modelValue: {
      type: String,
      required: false,
      default: ""
    }
  },

  methods: {
    updateInput(value) {
      this.input = value;
      this.$emit('update:modelValue', this.input);
    },
    clearInput() {
      this.updateInput('');
      this.$emit('submit', this.input);
    }
  }
}
</script>

<style scoped>
.searchbar-container {
  margin: 8px 0 20px;
  height: 50px;
}

.searchbar {
  background-color: #ECECECFF;
  border-radius: 8px;
  border: none;
  outline-color: var(--neutral-200);
  padding: 13px 15px;
  font-size: 16px;
  line-height: 1.5rem;
}

.searchbar-clear {
  top: 13px;
  right: 15px;
  height: 24px;
  width: 24px;
}

.searchbar-clear svg {
  fill: #000;
  height: 100%;
  width: 100%;
}

.searchbar-enter {
  background-color: var(--primary-400);
  width: 50px;
  height: 50px;
  border-radius: 6px;
  margin-left: 10px;
}

.searchbar-enter svg {
  color: #fff;
  font-size: 22px;
}
</style>