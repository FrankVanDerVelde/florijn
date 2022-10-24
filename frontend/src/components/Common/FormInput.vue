<template>
    <div class="input-container">
        <label v-bind:for="nameToId" class="mb-[8px]">{{ capitalizedName }}</label>
        <input v-bind:type="type" v-bind:id="nameToId" v-bind:name="nameToId" v-bind:placeholder="capitalizedName" class="pl-[7px]">
    </div>
</template>
    
<script>
export default {
    name: "FormInput",
    props: {
        name: String,
        type: {
            type: String,
            default: "text"
        }
    },
    computed : {
        nameToId() {
            return this.name.replace(/\s+/g, '-').toLowerCase();
        },
        capitalizedName() {
            return this.name.charAt(0).toUpperCase() + this.name.slice(1);
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
    color: #BFBFBF;
    border: 1px solid #BFBFBF;
    border-radius: 6px;
    font-family: 'Roboto', sans-serif;
    height: 40px;
    font-size: 16px;
 }

</style>