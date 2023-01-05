<template>
  <div class="flex sm:justify-center mt-14">
    <div>
      <div class="text-[34px] font-bold">Specialist toevoegen</div>
      <form class="pt-10 flex flex-col gap-5" @submit.prevent="addSpecialist">
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
        </div>

        <div class="flex flex-col">
          <label for="Persoonsgegevens" class="font-medium text-lg mb-2">Login gegevens</label>
          <div class="flex flex-col gap-2">
            <input type="text" v-model="email" placeholder="E-mail" name="email">
            <input type="text" v-model="password" placeholder="Wachtwoord" name="password">
          </div>
        </div>

        <div class="flex gap-2 w-full justify-center">
          <button type="submit" class="primary-button">Bevestigen</button>
          <button @click="handleCancelTapped" class="secondary-button">annuleren</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import Asset from "../../Common/Asset.vue";

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
      avatarBase64Encoded: null
    }
  },

  methods: {
    async handleSaveTapped() {
      await this.addSpecialist();
    },

    async addSpecialist() {
      await this.userRepository.addSpecialist(
          this.firstName,
          this.lastName,
          this.email,
          this.password,
          this.avatarUrl
      );
      this.navigateToSpecialistList();
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
</style>
