

<template>

    <div class="text-[34px] font-bold mb-[10px]">Profiel</div>
    <div class="name-and-picture-container flex items-center mb-4">
        <img class="rounded-full w-[80px] h-[80px] mr-4"
            src="https://media.istockphoto.com/vectors/thumbnail-image-vector-graphic-vector-id1147544807?k=20&m=1147544807&s=612x612&w=0&h=pBhz1dkwsCMq37Udtp9sfxbjaMl27JUapoyYpQm0anc=">
        <div>
            <div class="text-[26px] font-bold">Naam placeholder</div>
            <div class="text-[14px] text-primary-400 font-bold">Title placeholder</div>
        </div>
    </div>


    <div class="grid grid-cols-2 gap-4 row-1">
        <FormInput name="voornaam"></FormInput>
        <FormInput name="achternaam"></FormInput>
        <FormInput name="Email" type="email"></FormInput>
    </div>

    <span class="text-[18px] font-bold">Woongegevens</span>
    

    <div class="grid grid-cols-2 gap-4 row-1">
        <FormInput name="woonplaats"></FormInput>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1"> 
        <FormInput name="Straat"></FormInput>
    </div>

    <div class="grid grid-cols-3 gap-4">
        <FormInput name="huisnummer"></FormInput>
        <FormInput name="toevoeging"></FormInput>
        <FormInput name="postcode"></FormInput>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1">
        <SaveButton></SaveButton>
    </div>

</template>
  
<style scoped>

.input-container {
    display: flex;
    margin-bottom: 1em;
}

.input-container.duo {}

.input-container.duo>div:first-child {
    margin-right: 1em;
}

</style>

<script>
export default {
    name: "Profile",
    components: {
    FormInput,
    SaveButton
},
    data() {
        return {

        }
    }
}

import FormInput from "../../Common/FormInput.vue";
import SaveButton from "../../Common/SaveButton.vue";

</script>