<template>
  <div class="page-main-mw p-4">
    <div class="container">
      <form @submit.prevent=sendMailButton()>
        <p class="back-text" @click="backToInlogPage()"> &ltTerug naar inloggen</p>
        <p class="header-text">Wachtwoord vergeten?</p>
        <p class="text-changepassword">U kunt uw wachtwoord herstellen door uw e-mailadres in te voeren vanuit uw
          account, dan ontvangt u van ons een link om uw wachtwoord opnieuw in te stellen.</p>
        <div class="inputfield-wachtwoord">
          <div class="input-container">
            <label class="mb-[12px]">Emailadres</label>
            <input v-model="email" type="email" required class="pl-[7px]">
          </div>
        </div>
        <p class="text-center mt-1 text-app_red-500">&nbsp {{ validationText }}</p>
        <div class="submit-button">
          <button class="bg-primary-500 text-neutral-50 font-semibold hover:bg-primary-700 px-5 py-2.5
          text-sm leading-5 rounded-lg w-full">Verstuur email
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>

export default {
  name: "ForgotPassword",
  methods: {
    backToInlogPage() {
      this.$router.push("/login");
    },
    sendMailButton() {
      if (this.email !== '') {
        this.validationText = '';
        this.$router.push(this.$route.matched[0].path + "/cp");
      }
    }
  },
  data() {
    return {
      email: '',
      validationText: '',
    };
  }
}
</script>

<style scoped>

.container {
  display: flex;
  margin-top: 100px;
  justify-content: center;
  align-items: center;
}


.header-text {
  font-weight: bold;
  font-size: 20px;
  margin: 2%;
}


.inputfield-wachtwoord {
  margin: 3% 3% 9%;
}

.submit-button {
  justify-content: center;
  align-items: center;
  margin: 0.5vh;
  text-align: center;
}

.text-changepassword {
  color: var(--neutral-400);
  font-size: 14px;
  margin: 2% 2% 5%;
  max-width: 300px;
}

.back-text {
  color: var(--primary-500);
  margin: 1%;
  font-weight: bold;
  font-size: 14px;
}

.back-text:hover {
  color: var(--primary-800);
}

.input-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.input-container label {
  font-weight: 600;
  color: var(--neutral-400);
  font-size: 14px
}

.input-container input {
  width: 100%;
  color: black;
  border: 1px solid var(--neutral-200);
  border-radius: 6px;
  height: 40px;
  font-size: 16px;
}

</style>