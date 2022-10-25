

<template>

    <div class="text-[34px] font-bold mb-[10px]">Profiel</div>
    <div class="name-and-picture-container flex items-center mb-4">
        <img class="rounded-full w-[80px] h-[80px] mr-4"
            :src="user.profilePicture">
        <div>
            <div class="text-[26px] font-bold">{{ user.firstName}} {{ user.lastName}} </div>
            <div class="text-[14px] text-primary-400 font-bold">{{ user.title }}</div>
        </div>
    </div>


    <div class="grid grid-cols-2 gap-4 row-1">
        <FormInput name="voornaam" :value="user.firstName"></FormInput>
        <FormInput name="achternaam" :value="user.lastName"></FormInput>
        <FormInput name="Email" type="email" :value="user.email"></FormInput>
    </div>

    <span class="text-[18px] font-bold">Woongegevens</span>
    

    <div class="grid grid-cols-2 gap-4 row-1">
        <FormInput name="woonplaats" :value="user.place"></FormInput>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1"> 
        <FormInput name="Straat" :value="user.street"></FormInput>
    </div>

    <div class="grid grid-cols-3 gap-4">
        <FormInput name="huisnummer" :value="user.houseNumber"></FormInput>
        <FormInput name="toevoeging" :value="user.addition"></FormInput>
        <FormInput name="postcode" :value="user.postalCode"></FormInput>
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
            user: {
                profilePicture: "https://mytechguy.com/wp-content/uploads/2015/01/Lou-Face.jpg",
                firstName: "Martíno",
                lastName: "Manzana",
                title: "iOS Developer",
                email: "mmanzana@gmail.com",
                place: "Amsterdam",
                street: "Jan van Galenstraat",
                houseNumber: 53,
                addition: "E",
                postalCode: "1204EX"
            }
        }
    }
}

import FormInput from "../../Common/FormInput.vue";
import SaveButton from "../../Common/SaveButton.vue";

</script>