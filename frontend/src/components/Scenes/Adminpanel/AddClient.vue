<template>
  <div class="flex w-full h-full justify-center">
    <div class="pl-6">
      <p class="text-[34px] font-bold mt-[50px]">Klant toevoegen</p>
      <div class="flex">
        <div class="profile-container flex self-center">
          <div class="grid grid-cols-3 gap-4">
            <form class="pt-10 flex flex-col gap-5" @submit.prevent="handleAddClientClicked">

              <div class="flex flex-col gap-3">
                <div class="flex gap-3">
                  <div class="flex flex-col gap-1 items-start">
                    <label class="mb-2 text-sm font-medium text-gray-900" for="file_input">Logo</label>
                    <div class="flex flex-col gap-2 items-center">
                      <div @click="handlePickLogoClicked"
                           class="w-[90px] h-[90px] rounded rounded-xl bg-primary-100 flex justify-center items-center cursor-pointer">
                        <font-awesome-icon v-if="!logoBase64Encoded" class="text-primary-500 text-3xl" icon="fa-image"/>
                        <Asset
                            v-if="logoBase64Encoded"
                            :src="logoBase64Encoded"
                            class="w-full h-full rounded rounded-xl object-cover"
                            alt="Client logo"></Asset>
                      </div>
                      <input
                          hidden
                          class="w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
                          id="file_input_logo"
                          type="file"
                          @change="handleAvatarChanged"
                      >
                    </div>
                  </div>
                  <help-tip
                      data-tooltip-target="tooltip-right"
                      data-tooltip-placement="right"
                      :tip="'Deze banner wordt gebruikt voor nieuwe projecten.'">
                    <div class="flex flex-col gap-1 items-start">
                      <label class="mb-2 text-sm font-medium text-gray-900" for="file_input">Banner <span class="text-neutral-400">(niet verplicht)</span></label>
                      <div class="flex flex-col gap-2 items-center">
                        <div @click="handlePickBannerClicked"
                             class="w-[300px] h-[90px] rounded rounded-xl bg-primary-100 flex justify-center items-center cursor-pointer">
                          <font-awesome-icon v-if="!bannerBase64Encoded" class="text-primary-500 text-3xl" icon="fa-panorama"/>
                          <Asset
                              v-if="bannerBase64Encoded"
                              :src="bannerBase64Encoded"
                              class="w-full h-full rounded rounded-xl object-cover"
                              alt="Client logo"></Asset>
                        </div>
                        <input
                            hidden
                            class="w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
                            id="file_input_banner"
                            type="file"
                            @change="handleBannerChanged"
                        >
                      </div>
                    </div>
                  </help-tip>
                </div>
                <p v-if="errors.fileError" class="validation-label">{{errors.fileError}}</p>
              </div>
              <div class="flex flex-col gap-1">
                <label for="bedrijfsnaam" class="font-medium">Naam bedrijf</label>
                <div class="flex flex-col gap-2">
                  <input v-model="form.companyName" type="text" placeholder="naam bedrijf" id="bedrijfsnaam"
                         name="name">
                  <p v-if="errors.companyNameError" class="validation-label">{{ errors.companyNameError }}</p>
                </div>
              </div>

              <div class="flex flex-col gap-1 w-full">
                <label class="font-medium">Gegevens</label>
                <div class="flex flex-col gap-2 w-full">
                  <div>
                    <input type="email" class="w-full" autocomplete="email" v-model="form.email" placeholder="e-mail" name="email">
                    <p v-if="errors.passwordError" class="validation-label">{{ errors.companyNameError }}</p>
                  </div>
                  <div>
                    <input autocomplete="new-password" type="password" class="w-full"  v-model="form.password" placeholder="wachtwoord"
                           name="password">
                    <p v-if="errors.passwordError" class="validation-label">{{ errors.companyNameError }}</p>
                  </div>
                </div>
              </div>

              <div class="flex flex-col gap-1">
                <p v-if="errors.submitError" class="validation-label">{{ errors.submitError }}</p>
                <div class="flex gap-2">
                  <button type="submit" class="primary-button">Toevoegen</button>
                  <button @click="handleCancelClicked" class="secondary-button">Annuleren</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Asset from "../../Common/Asset.vue";
import HelpTip from "../../Common/HelpTip.vue";

export default {
  name: "AddClient",
  components: {HelpTip, Asset},
  inject: ['userRepository'],

  data() {
    return {
      form: {
        companyName: null,
        email: null,
        password: null,
        avatarUrl: null,
        bannerSrc: null,
      },
      isFormValid: null,
      errors: {
        companyNameError: null,
        emailError: null,
        passwordError: null,
        submitError: null,
        fileError: null
      },
      logoBase64Encoded: null,
      bannerBase64Encoded: null,
    }
  },

  methods: {
    async handleAddClientClicked() {
      this.clearAllErrors();
      this.validateForm();
      if (this.isFormValid) {
        await this.addClient();
      }
    },

    clearAllErrors() {
      Object.keys(this.errors).forEach(key => {
        this.errors[key] = null;
      });
    },

    validateForm() {
      this.isFormValid = null;
      this.validateLogo();
      this.validateCompanyName();
      this.validateEmail();
      this.validatePassword();
      if (this.isFormValid === null) {
        this.isFormValid = true;
      }
    },

    validateLogo() {
      if (!this.logoBase64Encoded) {
        this.isFormValid = false;
        this.errors.fileError = 'Logo is verplicht';
      }
    },

    validateCompanyName() {
      if (!this.form.companyName) {
        this.isFormValid = false;
        this.errors.companyNameError = 'Naam van het bedrijf is verplicht.';
      }
    },

    validateEmail() {
      if (!this.form.email) {
        this.isFormValid = false;
        this.errors.emailError = 'Email is verplicht.'
      }
    },

    validatePassword() {
      if (!this.form.password) {
        this.isFormValid = false;
        this.errors.passwordError = 'Wachtwoord is verplicht.';
      } else if (this.form.password.length < 4) {
        this.isFormValid = false;
        this.errors.passwordError = 'Wachtwoord moet minimaal vier karakters hebben';
      }
    },

    async handleAvatarChanged(event) {
      if (event.target.files.length === 0) {
        delete this.project.logoSrc;
        return;
      }

      if (!this.checkSupportedMediaType(event))
        return;

      this.form.avatarUrl = event.target.files[0];
      this.logoBase64Encoded = `${await this.getBase64(event.target.files[0])}`;
    },

    async handleBannerChanged(event) {
      if (event.target.files.length === 0) {
        delete this.project.logoSrc;
        return;
      }

      if (!this.checkSupportedMediaType(event))
        return;

      this.form.bannerSrc = event.target.files[0];
      this.bannerBase64Encoded = `${await this.getBase64(event.target.files[0])}`;
    },

    checkSupportedMediaType(event) {
      const fileExtension = event.target.files[0].name.split('.').pop();
      if (!['svg', 'png', 'webp', 'jpg', 'jpeg'].includes(fileExtension)) {
        this.errors.fileError = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
        return false;
      }
      return true;
    },

    async addClient() {
      try {
        await this.userRepository.addClient(this.form.companyName, this.form.email, this.form.password, this.form.avatarUrl, this.form.bannerSrc);
        this.navigateToCustomerList();
      } catch (e) {
        console.error(e);
        this.showErrorAddingClient();
      }
    },

    navigateToCustomerList() {
      this.$router.push({name: 'admin-customer-list'});
    },

    handleCancelClicked() {
      this.navigateToCustomerList();
    },

    showErrorAddingClient() {
      this.errors.submitError = 'Er is iets mis gegaan met het aanmaken van de klant, probeer het later opnieuw.'
    },

    async handlePickLogoClicked() {
      window.document.getElementById('file_input_logo').click();
    },

    async handlePickBannerClicked() {
      window.document.getElementById('file_input_banner').click();
    },

    async getBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
      });
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
