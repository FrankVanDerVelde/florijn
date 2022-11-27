<template>
    <div class="input-container">
        <label v-bind:for="nameToId" class="mb-[8px]">{{ capitalizedName }}</label>
        <input v-bind:type="type" @input="updateInput($event.target.value)" v-bind:id="nameToId" v-bind:name="nameToId"
            v-bind:placeholder="capitalizedName" :value="value" class="pl-[7px]">
    </div>
</template>
    
<script>
export default {
    name: "FormInput",
    emits: ['update:modelValue'],
    methods: {
        updateInput(value) {
            this.value = value;
            this.$emit('update:modelValue', this.value);
        }
    },
    props: {
        name: String,
        type: {
            type: String,
            default: "text"
        },
    modelValue: {
      type: [String, Number, null],
      required: false,
      default: ""
    }
    },
    computed: {
        nameToId() {
            return this.name.replace(/\s+/g, '-').toLowerCase();
        },
        capitalizedName() {
            return this.name.charAt(0).toUpperCase() + this.name.slice(1);
        }
    },
    data() {
        return {
            value: this.modelValue,
        }
    }
}
</script>

<style scoped>
.input-container {
    width: 100%;
    display: flex;
    flex-direction: column;
}

.input-container label {
    font-family: 'Roboto', sans-serif;
    font-weight: 600;
    color: #7B8794;
    font-size: 14px
}

.input-container input {
    width: 100%;
    color: #474747;
    border: 1px solid #BFBFBF;
    border-radius: 6px;
    font-family: 'Roboto', sans-serif;
    height: 40px;
    font-size: 16px;
}
</style>