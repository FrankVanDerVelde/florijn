<template>
    <div class="input-container" :class="{'invalid': !valid}">
        <label v-bind:for="nameToId" class="mb-[8px]">{{ capitalizedName }}</label>
        <input v-bind:type="type" @input="updateInput($event.target.value)" v-bind:id="nameToId" v-bind:name="nameToId"
            v-bind:placeholder="capitalizedName" :value="modelValue" class="pl-[7px]" @blur="validateSelf">
    </div>
</template>
    
<script>
export default {
    name: "FormInput",
    emits: ['update:modelValue'],
    data() {
        return {
            errors: []
        }
    },
    methods: {
        updateInput(value) {
            this.value = value;
            this.$emit('update:modelValue', this.value);
        },
        validateSelf() {
            const newErrors = [];

            this.validationRules.forEach(rule => {
                switch (true) {
                    case (rule === 'required'):
                        !this.modelValue && newErrors.push("Vereist veld");
                        break;
                    case (rule === 'zipcode-dutch'):
                        const dutchZipCodeRegex = new RegExp(/^[1-9][0-9]{3} ?(?!sa|sd|ss)[a-z]{2}$/i);
                        !dutchZipCodeRegex.test(this.modelValue) && newErrors.push("Geen geldige postcode");
                        break;
                    case (rule === 'email'):
                        const emailRegex = new RegExp(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
                        !emailRegex.test(this.modelValue) && newErrors.push("Geen geldig email");
                        break;
                    case (rule.type === "min-len"):
                        this.modelValue.length < rule.len && newErrors.push(`Input should be at least ${rule.len} characters long`);
                        break;

                    case (rule.type === "max-len"):
                        this.modelValue.length > rule.len && newErrors.push(`Input should be less than ${rule.len} characters long`);
                        break;
                }
            });
            this.errors = newErrors;
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
        },
        validationRules: {
            type: Array,
            default: []
        }
    },
    computed: {
        nameToId() {
            return this.name.replace(/\s+/g, '-').toLowerCase();
        },
        capitalizedName() {
            return this.name.charAt(0).toUpperCase() + this.name.slice(1);
        },
        valid() {
            return this.errors.length === 0;
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

.invalid label {
    color: red;
}

.invalid input {
    border: 1px solid red;
}
</style>