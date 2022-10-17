<template>
  <div class="page-main-mw p-4">
    <div class="container">
      <div class="login-form">
        <div class="border-bottom">
          <p class="header-text">Wachtwoord vergeten?</p>
          <p class="back-text" @click="backToInlogPage()"> &ltBack</p>
        </div>
        <p class="text-forgotpassword">U kunt uw wachtwoord herstellen door uw e-mailadres in te voeren vanuit uw
          account, dan ontvangt u van ons een link om uw wachtwoord opnieuw in te stellen.</p>
        <div class="inputfield-wachtwoord">
          <FormInput name="wachtwoord"></FormInput>
        </div>
        <div class="inputfield-wachtwoord-herhaal">
          <FormInput name="herhaal wachtwoord"></FormInput>
        </div>
        <div class="submit-button">
          <primary-button title="Verstuur email"></primary-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FormInput from "./Common/FormInput.vue";
import PrimaryButton from "./Common/PrimaryButton.vue";

export default {
  name: "ForgotPassword",
  components: {
    FormInput,
    PrimaryButton
  },
  methods: {
    backToInlogPage() {
      this.$router.push("/login")
    }
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

.login-form {
  border-radius: 6px;
  min-width: 300px;
  border: 2px solid #BFBFBF;
  box-shadow: 0 7px 8px 2px rgba(0, 0, 0, 0.12);
}

.header-text {
  font-weight: bold;
  font-size: 20px;
  margin: 2%;
}

.border-bottom {
  border-bottom: 1px solid var(--neutral-100);
}

.inputfield-wachtwoord-herhaal {
  margin: 3% 3% 9%;
}

.inputfield-wachtwoord {
  margin: 3% 3% 9%;
}

.submit-button {
  justify-content: center;
  align-items: center;
  margin: 3%;
  text-align: center;
}

.back-text {
  color: #F35627;
  margin: 1%;
  font-weight: bold;
  font-size: 15px;
  min-height: 50px;
}

.text-forgotpassword {
  color: #7B8794;
  margin: 2% 2% 5%;
  max-width: 300px;
}


</style>