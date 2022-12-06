

<template>
    <form>
        <div class="text-[34px] font-bold mb-[10px]">Profiel</div>
        <div class="name-and-picture-container flex items-center mb-4">
            <div>
                <img class="rounded-full w-[80px] h-[80px] mr-4" :src="user.avatarUrl">
                <div class="profile-picture-container">
                    <label for="profile-picture-upload-input">
                        <font-awesome-icon icon="fa-file-image" class="profile-picture-upload-icon" />
                    </label>
                    <input @change="e => updateAvatar(e)" type="file" name="profile-picture-upload-input"
                        id="profile-picture-upload-input" accept=".svg,.png,.webp,jpg,.jpeg">

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
            <FormInput name="Email" type="email" v-model="user.email" :validationRules="['required', 'email']">
            </FormInput>
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
            <FormInput name="postcode" v-model="address.postalCode" :validationRules="['required', 'zipcode-dutch']">
            </FormInput>
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
            address: {},
            avatarFile: null
        }
    },
    methods: {
        async handleProfileUpdate(formValues) {
            this.user.address = this.address;
            const adjustedUser = this.user;
            ['expertises', 'password', 'role', 'skills'].forEach(key => {
                adjustedUser[key] && (delete adjustedUser[key]); 
            }) 
   
            const formData = new FormData();
            console.log(adjustedUser);
            formData.append('address', JSON.stringify(adjustedUser.address));
            delete adjustedUser.address;

            formData.append('user', JSON.stringify({
                adjustedUser
            }));

            formData.append('id', 
                adjustedUser.id
            );


            if (this.avatarFile != null) formData.append('avatarFile', this.avatarFile);

            console.log(this.avatarFile)
            this.userFetchService.fetchJsonFile(`/${this.user.id}/edit`, "PUT", formData).then((response) => {
                localStorage.setItem("user", JSON.stringify(response));
            })
        },
        async updateAvatar(event) {
            if (event.target.files.length === 0) {
                this.logoFile = null;
                delete this.user.avatarUrl;
                return;
            }

            const fileExtension = event.target.files[0].name.split('.').pop();
            if (!['svg', 'png', 'webp', 'jpg', 'jpeg'].includes(fileExtension)) {
                // this.errors.logo = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
                return;
            }

            this.avatarFile = event.target.files[0];
            this.user.avatarUrl = `${await this.getBase64(event.target.files[0])}`;
        },
        async getBase64(file) {
            // convert javascript file to base64 string
            return new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = () => resolve(reader.result);
                reader.onerror = error => reject(error);
            });
        }
    }
}

import { VueElement } from "vue";
import FormInput from "../../Common/FormInput.vue";
import SaveButton from "../../Common/SaveButton.vue";

</script>