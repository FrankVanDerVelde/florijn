<template>
  <div class="page-main-mw p-4">
    <div class="container">
      <div class="login-form">
        <div class="border-bottom">
          <p class="header-text">Vul inloggegevens in</p>
        </div>
        <div class="inputfield-mail">
          <FormInput name="emailadres"></FormInput>
        </div>
        <div class="inputfield-wachtwoord">
          <FormInput name="wachtwoord"></FormInput>
        </div>
        <div class="submit-button">
          <primary-button title="Log in"></primary-button>
        </div>
        <div class="forgot-password" @click="forgotPassword()">Wachtwoord vergeten?</div>
      </div>
    </div>
  </div>
</template>

<script>
import FormInput from "./Common/FormInput.vue";
import PrimaryButton from "./Common/PrimaryButton.vue";

export default {
  name: "LogIn.vue",
  components: {
    FormInput,
    PrimaryButton
  },
  methods: {
    forgotPassword() {
      this.$router.push(this.$route.matched[0].path + "/forgotpassword")
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
  margin: 3%;
  min-height: 50px;
}

.border-bottom {
  border-bottom: 1px solid var(--neutral-100);
}

.inputfield-mail {
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

.forgot-password {
  margin: 3% 3% 3%;
  text-align: center;
  color: #F35627;
  font-weight: bold;
  font-size: 17px;
}

</style>