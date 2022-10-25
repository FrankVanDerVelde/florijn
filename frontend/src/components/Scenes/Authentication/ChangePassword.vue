<template>
  <div class="page-main-mw p-4">
    <div class="container">
      <div class="change-password-form">
        <div class="border-bottom">
          <p class="header-text">Wachtwoord veranderen</p>
        </div>
        <p class="text-changepassword">Gelieve uw wachtwoord te herstellen in onderstaande invulvelden</p>
        <div class="inputfield-wachtwoord">
          <div class="input-container">
            <label class="mb-[12px]">Wachtwoord</label>
            <input v-model="password" type="password" class="pl-[7px]">
          </div>
        </div>
        <div class="inputfield-wachtwoord">
          <div class="input-container">
            <label class="mb-[12px]">Herhaal wachtwoord</label>
            <input v-model="repeatPassword" type="password" class="pl-[7px]">
            <p class="wrongText">{{ validationText }}</p>
          </div>
        </div>
        <div class="submit-button">
          <button class="bg-primary-500 text-neutral-50 font-semibold hover:bg-primary-700 px-5 py-2.5
          text-sm leading-5 rounded-lg w-fit" @click="saveButton()">Opslaan
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "ChangePassword",
  methods: {
    saveButton() {
      if (this.password === '' || this.repeatPassword === '') {
        this.validationText = 'De velden zijn niet volledig ingevuld!';
      } else if (this.password !== this.repeatPassword) {
        this.validationText = 'De opgegeven wachtwoorden zijn niet gelijk!';
        this.repeatPassword = '';
      } else {
        this.validationText = '';
        this.$router.push("/login");
      }
    }
  },
  data() {
    return {
      email: '',
      password: '',
      repeatPassword: '',
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

.change-password-form {
  border-radius: 6px;
  width: 300px;
  border: 2px solid var(--neutral-200);
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

.inputfield-wachtwoord {
  margin: 3% 3% 9%;
}

.submit-button {
  justify-content: center;
  align-items: center;
  margin: 3%;
  text-align: center;
}

.text-changepassword {
  color: var(--neutral-400);
  margin: 2% 2% 5%;
  max-width: 300px;
}

.input-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.input-container label {
  font-family: 'Roboto', sans-serif;
  font-weight: 600;
  color: var(--neutral-400);
  font-size: 14px
}

.input-container input {
  width: 100%;
  color: var(--neutral-200);
  border: 1px solid var(--neutral-200);
  border-radius: 6px;
  font-family: 'Roboto', sans-serif;
  height: 40px;
  font-size: 16px;
}

.wrongText {
  color: red;
}

</style>