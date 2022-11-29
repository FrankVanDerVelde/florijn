

<template>
    	<form>
    <div class="text-[34px] font-bold mb-[10px]">Profiel</div>
    <div class="name-and-picture-container flex items-center mb-4">
        <div>
        <img class="rounded-full w-[80px] h-[80px] mr-4" :src="user.profilePicture">
        <div class="profile-picture-container">
            <label for="profile-picture-upload-input">
                <font-awesome-icon icon="fa-file-image" class="profile-picture-upload-icon"/>
            </label>
            <input @change="e => updateLogo(e)"
                   type="file"
                   name="profile-picture-upload-input"
                   id="profile-picture-upload-input"
                   accept=".svg,.png,.webp,jpg,.jpeg">
            
        </div>
        </div>
        <div>
            <div class="text-[26px] font-bold">{{ user.firstName }} {{ user.lastName }} </div>
            <div class="text-[14px] text-primary-400 font-bold">{{ user.title }}</div>
        </div>
    </div>
    

    <div class="grid grid-cols-2 gap-4 row-1">
        <FormInput name="voornaam" v-model="user.firstName" :validationRules="['required']"></FormInput>
        <FormInput name="achternaam" v-model="user.lastName" :validationRules="['required']"></FormInput>
        <FormInput name="Email" type="email" v-model="user.email" :validationRules="['required', 'email']"></FormInput>
    </div>

    <span class="text-[18px] font-bold">Woongegevens</span>


    <div class="grid grid-cols-2 gap-4 row-1">
        <FormInput name="woonplaats" v-model="address.place"></FormInput>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1">
        <FormInput name="Straat" v-model="address.street"></FormInput>
    </div>

    <div class="grid grid-cols-3 gap-4">
        <FormInput name="huisnummer" type="number" v-model="address.houseNumber"></FormInput>
        <FormInput name="toevoeging" v-model="address.houseNumberAddition"></FormInput>
        <FormInput name="postcode" v-model="address.postalCode" :validationRules="['required', 'zipcode-dutch']"></FormInput>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1">
        <SaveButton :onClick="handleProfileUpdate"></SaveButton>
    </div>
</form>
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

.profile-picture-container {
    position: relative;
}
.profile-picture-container label {
    position: absolute;
    color: gray;
    right: 24px;
    bottom: 0px;
    font-size: 25px;
}

.profile-picture-container input {
    display: none;
}
</style>

<script>
export default {
    name: "Profile",
    components: {
        FormInput,
        SaveButton
    },
    inject: ['userFetchService'],
    async created() {
        const newAddress = await this.userFetchService.fetchJson(`/address/${this.user.id}`);
        this.address = newAddress;
    },
    data() {
        return {
            user: JSON.parse(localStorage.getItem("user")),
            address: {}
        }
    },
    methods: {
        async handleProfileUpdate(formData) {
            this.user.address = this.address;
            this.userFetchService.fetchJsonMethod(`/${this.user.id}/edit`, "PUT", this.user).then((response) => {
                localStorage.setItem("user", JSON.stringify(response));
            })
        }
    }
}

import { VueElement } from "vue";
import FormInput from "../../Common/FormInput.vue";
import SaveButton from "../../Common/SaveButton.vue";

</script>