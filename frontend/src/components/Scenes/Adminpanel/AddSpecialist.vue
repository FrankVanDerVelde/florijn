<template>
  <div class="page-main-mw flex">
    <div class="mt-14 ml-14">
      <div class="text-[34px] font-bold">Specialist toevoegen</div>
      <form class="pt-10 flex flex-col gap-5" @submit.prevent="handleSaveTapped">
        <div class="flex flex-col">
          <label for="Persoonsgegevens" class="font-medium text-lg mb-2">Persoonsgegevens</label>
          <div class="flex gap-4 items-start">
            <div class="flex flex-col gap-2 items-center">
              <div @click="handlePickImageClicked"
                   class="w-[90px] h-[90px] rounded rounded-xl bg-primary-100 flex justify-center items-center cursor-pointer">
                <font-awesome-icon v-if="!avatarBase64Encoded" class="text-primary-500 text-3xl" icon="fa-image"/>
                <Asset class="w-full h-full rounded rounded-xl object-cover" v-if="avatarBase64Encoded" :src="avatarBase64Encoded"
                       alt="project logo"></Asset>
              </div>
              <input
                  hidden
                  class="w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
                  id="file_input"
                  type="file"
                  @change="handleAvatarChanged"
              >
            </div>
            <div class="flex flex-col h-auto gap-2 w-full">
              <input type="text" v-model="firstName" placeholder="Voornaam" id="voornaam" name="firstname">
              <input type="text" v-model="lastName" placeholder="Achternaam" id="achternaam" name="lastname">
            </div>
          </div>
          <p v-if="errors.personalInfo" class="validation-label">{{errors.personalInfo}}</p>
        </div>

        <div class="flex flex-col">
          <label for="Persoonsgegevens" class="font-medium text-lg mb-2">Login gegevens</label>
          <div class="flex flex-col gap-2 w-full">
            <div>
              <input class="w-full" type="text" v-model="email" placeholder="E-mail" name="email">
              <p v-if="errors.email" class="validation-label">{{errors.email}}</p>
            </div>

            <div>
              <input class="w-full" type="password" v-model="password" placeholder="Wachtwoord" name="password">
              <p v-if="errors.password" class="validation-label">{{errors.password}}</p>
            </div>

          </div>
        </div>

        <div class="flex gap-2 w-full">
          <button type="submit" class="primary-button">Bevestigen</button>
          <button @click="handleCancelTapped" class="secondary-button">annuleren</button>

        </div>
        <p v-if="errors.submitError" class="validation-label">{{errors.submitError}}</p>
      </form>
    </div>
  </div>
</template>

<script>
import Asset from "../../Common/Asset.vue";
import CryptoJS from 'crypto-js'

export default {
  name: "AddSpecialist",
  components: {Asset},
  inject: ['userRepository'],
  props: {
    userId: {
      type: String,
      default: "-1"
    }
  },

  data(){
    return {
      firstName: null,
      lastName: null,
      email: null,
      password: null,
      avatarUrl: null,
      avatarBase64Encoded: null,
      errors: {
        personalInfo: null,
        email: null,
        password: null,
        submitError: null
      },
      isFormValid: null
    }
  },

  methods: {
    async handleSaveTapped() {
      this.clearErrors();
      this.validateForm();
      if (this.isFormValid) {
        await this.addSpecialist();
      }
    },

    clearErrors() {
      Object.keys(this.errors).forEach(key => {
        this.errors[key] = null;
      });
    },

    validateForm() {
      this.isFormValid = null;
      this.validateAvatar()
      this.validateFirstName()
      this.validateLastName()
      this.validateEmail()
      this.validatePassword()
      if (this.isFormValid === null) {
        this.isFormValid = true;
      }
    },

    validateAvatar() {
      if (!this.avatarBase64Encoded) {
        this.isFormValid = false;
        this.errors.personalInfo = 'Kies een profiel foto.';
      }
    },

    validateFirstName() {
      if (!this.firstName) {
        this.isFormValid = false;
        this.errors.personalInfo = 'Vul voornaam in';
      }
    },

    validateLastName() {
      if (!this.lastName) {
        this.isFormValid = false;
        this.errors.personalInfo = 'Vul achternaam in';
      }
    },

    validateEmail() {
      if (!this.email) {
        this.isFormValid = false;
        this.errors.email = 'Vul email in';
        return;
      }

      const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      let isEmailValid = re.test(String(this.email).toLowerCase());
      if (!isEmailValid) {
        this.isFormValid = false;
        this.errors.email = 'Email niet geldig';
      }
    },

    validatePassword() {
      if (!this.password) {
        this.isFormValid = false;
        this.errors.password = 'Vul wachtwoord in';
        return;
      }
      if (this.password.length < 4) {
        this.isFormValid = false;
        this.errors.password = 'Wachtwoord moet minimaal 4 karakters zijn.';
      }
    },

    async addSpecialist() {
      await this.userRepository.addSpecialist(
          this.firstName,
          this.lastName,
          this.email,
          this.hashMethod(this.password),
          this.avatarUrl
      );
      this.navigateToSpecialistList();
    },
    hashMethod(password){
      if (password != null){
        const salt = "mixer";
        const key = CryptoJS.PBKDF2(password, salt, { keySize: 512/32, iterations: 1000 });
        return key.toString(CryptoJS.enc.Hex);
      }
    },

    handleCancelTapped() {
      this.navigateToSpecialistList();
    },

    navigateToSpecialistList() {
      this.$router.push({name: 'admin-employee-list'});
    },

    async handleAvatarChanged(event) {
      if (event.target.files.length === 0) {
        return;
      }

      const fileExtension = event.target.files[0].name.split('.').pop();
      if (!['svg', 'png', 'webp', 'jpg', 'jpeg'].includes(fileExtension)) {
        this.errors.logo = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
        return;
      }

      this.avatarUrl = event.target.files[0];
      this.avatarBase64Encoded = `${await this.getBase64(event.target.files[0])}`;
    },

    async getBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
      });
    },

    async handlePickImageClicked() {
      window.document.getElementById('file_input').click();
    },
  }
}
</script>

<style scoped>
input {
  @apply border border-neutral-300 p-2 rounded rounded-[4px];
}

.validation-label {
  @apply text-app_red-500;
}
</style>
