<template>
  <div class="flex w-full h-full justify-center">
    <div class="pl-6">
      <p class="text-[34px] font-bold mt-[50px]">Klant toevoegen</p>
      <div class="pt-[5em] flex">
        <div class="profile-container flex self-center w-4/5">
          <div class="flex">
            <div class="grid grid-cols-3 gap-4">
              <form class="flex flex-col gap-6" @submit.prevent="handleAddClientClicked">
                <div class="flex flex-col gap-1">
                  <label for="bedrijfsnaam" class="font-medium">Naam bedrijf</label>
                  <div class="flex flex-col gap-2">
                    <input v-model="form.companyName" type="text" placeholder="naam bedrijf" id="bedrijfsnaam"
                           name="name">
                    <p v-if="errors.companyNameError" class="validation-label">{{ errors.companyNameError }}</p>
                  </div>
                </div>

                <div class="flex flex-col gap-1">
                  <label class="font-medium">Gegevens</label>
                  <div class="flex flex-col gap-2">
                    <div>
                      <input type="email" autocomplete="email" v-model="form.email" placeholder="e-mail" name="email">
                      <p v-if="errors.passwordError" class="validation-label">{{ errors.companyNameError }}</p>
                    </div>
                    <div>
                      <input autocomplete="new-password" type="password" v-model="form.password" placeholder="wachtwoord"
                             name="password">
                      <p v-if="errors.passwordError" class="validation-label">{{ errors.companyNameError }}</p>
                    </div>
                  </div>
                </div>

                <div class="w-full">
                  <font-awesome-icon icon="fa-solid fa-image"/>
                  <label class="mb-2 text-sm font-medium text-gray-900" for="file_input">Client Avatar</label>
                  <input
                      class="w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none"
                      id="file_input"
                      type="file"
                  >
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
        <div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddClient",
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
        submitError: null
      }
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
      this.validateCompanyName();
      this.validateEmail();
      this.validatePassword();
      if (this.isFormValid === null) {
        this.isFormValid = true;
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
