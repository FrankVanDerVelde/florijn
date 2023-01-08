<template>
  <form>
    <div class="text-[34px] font-bold mb-[10px]">Profiel</div>
    <div class="name-and-picture-container flex items-center mb-4">
      <div class="relative">
        <Asset class="rounded-full w-[110px] h-[110px] mr-4" :src="avatar" alt="avatar"/>
        <div class="profile-picture-container absolute bottom-[-5px] right-[24px]">
          <label for="profile-picture-upload-input" class="cursor-pointer">
            <div class="bg-neutral-0 w-[30px] h-[30px] border-[1px] border-l-neutral-900 rounded-full">
              <font-awesome-icon icon="fa-pen-to-square"
                                 class="profile-picture-upload-icon absolute left-[5px] top-[4px] text-[20px]"/>
            </div>
          </label>
          <input @change="e => updateAvatar(e)" type="file" name="profile-picture-upload-input" class="hidden"
                 id="profile-picture-upload-input" accept=".svg,.png,.webp,jpg,.jpeg">

        </div>
      </div>
      <div>
        <div class="text-[26px] font-bold">{{ user.firstName }} {{ user.lastName }}</div>
        <div class="text-[14px] text-primary-400 font-bold">{{ user.title }}</div>
      </div>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1">
      <div v-if="['CLIENT'].includes(user.role)">
        <FormInput name="naam" v-model="user.name" :validationRules="['required']"></FormInput>
      </div>
      <div v-if="['SPECIALIST', 'ADMIN'].includes(user.role)">
        <FormInput name="voornaam" v-model="user.firstName" :validationRules="['required']"></FormInput>
        <FormInput name="achternaam" v-model="user.lastName" :validationRules="['required']"></FormInput>
      </div>

      <FormInput name="Email" type="email" v-model="user.email" :validationRules="['required', 'email']">
      </FormInput>
    </div>

    <div v-if="['SPECIALIST'].includes(user.role)">

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
        <FormInput name="postcode" v-model="address.postalCode"
                   :validationRules="['required', 'zipcode-dutch']">
        </FormInput>
      </div>

    </div>

    <div v-if="['CLIENT'].includes(user.role)" class="text-[34px] font-bold mb-[10px]">Banner</div>
    <div v-if="['CLIENT'].includes(user.role)" class="relative">
      <Asset class="w-full h-[218px] bg-cover rounded-[12px] object-cover" :src="banner" alt="banner"/>
      <div class="banner-container absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
        <label for="banner-upload-input" class="text-[120px] text-neutral-0 opacity-70">
          <font-awesome-icon icon="fa-pen-to-square" class="banner-upload-icon"/>
        </label>
        <input @change="e => updateBanner(e)" type="file" name="banner-upload-input" class="hidden"
               id="banner-upload-input" accept=".svg,.png,.webp,jpg,.jpeg">

      </div>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1 mt-9">
      <SaveButton class="cursor-pointer" :onClick="handleProfileUpdate"></SaveButton>
    </div>
  </form>
</template>

<style scoped>
.input-container {
  display: flex;
  margin-bottom: 1em;
}

.input-container.duo {
}

.input-container.duo > div:first-child {
  margin-right: 1em;
}
</style>

<script>
export default {
  name: "Profile",
  components: {
    FormInput,
    SaveButton,
    Asset
  },
  inject: ['fetchService', 'userFetchService'],
  async created() {
    if (this.user === null) {
      this.user = {};
    }
    if (this.user.role === "SPECIALIST") {
      const newAddress = await this.userFetchService.fetchJson(`/address/${this.user.id}`);
      this.address = newAddress;
    }
  },
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user")),
      address: {},
      avatarFile: null,
      bannerFile: null,
    }
  },
  computed: {
    avatar() {
      if (this.user.avatarUrl == null) return '/defaults/default-avatar.png';
      return this.user.avatarUrl;
    },
    banner() {
      if (this.user.bannerSrc == null) return '/defaults/default-banner.png';
      return this.user.bannerSrc;
    }
  },
  methods: {
    async handleProfileUpdate(formValues) {
      this.user.address = this.address;

      const adjustedUser = Object.assign({}, this.user);
      ['expertises', 'password', 'role', 'skills'].forEach(key => {
        adjustedUser[key] && (delete adjustedUser[key]);
      })

      const formData = new FormData();

      formData.append('address', JSON.stringify(adjustedUser.address));
      delete adjustedUser.address;

      formData.append('user', JSON.stringify(
          adjustedUser
      ));

      formData.append('id',
          adjustedUser.id
      );

      // console.log(Object.fromEntries(formData))

      if (this.avatarFile != null) formData.append('avatarFile', this.avatarFile);

      if (this.bannerFile != null) formData.append('bannerFile', this.bannerFile);

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
    async updateBanner(event) {
      if (event.target.files.length === 0) {
        this.bannerFile = null;
        delete this.user.bannerSrc;
        return;
      }

      const fileExtension = event.target.files[0].name.split('.').pop();
      if (!['svg', 'png', 'webp', 'jpg', 'jpeg'].includes(fileExtension)) {
        // this.errors.logo = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
        return;
      }

      this.bannerFile = event.target.files[0];
      this.user.bannerSrc = `${await this.getBase64(event.target.files[0])}`;
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

import {VueElement} from "vue";
import FormInput from "../../Common/FormInput.vue";
import SaveButton from "../../Common/SaveButton.vue";
import Asset from "../../Common/Asset.vue"

</script>