<template>
  <div class="page-main-mw p-4">
    <div class="container">
      <div class="login-form">
        <div class="border-bottom">
          <p class="header-text">Vul inloggegevens in</p>
        </div>
        <div class="inputfield-mail">
          <div class="input-container">
            <label class="mb-[12px]">Emailadres</label>
            <input v-model="email" class="pl-[7px]">
          </div>
        </div>
        <div class="inputfield-wachtwoord">
          <div class="input-container">
            <label class="mb-[12px]">Wachtwoord</label>
            <input v-model="password" type="password" class="pl-[7px]">
            <p class="wrongLogin">{{ validationText }}</p>
          </div>
        </div>
        <div class="submit-button">
          <button class="bg-primary-500 text-neutral-50 font-semibold hover:bg-primary-700 px-5 py-2.5
          text-sm leading-5 rounded-lg w-fit" @click="submitButton()">Log in
          </button>
        </div>
        <div class="forgot-password" @click="forgotPassword()">Wachtwoord vergeten?</div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "LogIn.vue",
  methods: {
    forgotPassword() {
      this.$router.push(this.$route.matched[0].path + "/forgotpassword");
    },
    submitButton() {
      if (this.email === '' || this.password === '') {
        this.validationText = 'De velden zijn niet volledig ingevuld!';
        return;
      }
      for (let i = 0; i < this.users.length; i++) {
        if (this.email === this.users[i].emailadress && this.password === this.users[i].password) {
          console.log("Ingelogd met het id: " + this.users[i].id);
          this.$router.push("/project-overview");
          return;
        }
      }
      this.validationText = 'De inloggegevens zijn onjuist ingevuld! Probeer het' +
          ' nogmaals met een ander emailadres/wachtwoord.';
      this.password = '';
    }
  },
  data() {
    return {
      email: '',
      password: '',
      validationText: '',
      users: [
        {
          id: 0,
          emailadress: "jveerman@outlook.com",
          password: "GebakkenEieren10",
        }, {
          id: 1,
          emailadress: "rtol@outlook.com",
          password: "Vuurtoren10",
        },
      ],
    };
  },
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
  width: 300px;
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

.forgot-password:hover {
  color: #C52707;
}

.wrongLogin {
  color: red;
}

.input-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.input-container label {
  font-family: 'Roboto', sans-serif;
  font-weight: 600;
  color: #7B8794;
  font-size: 14px
}

.input-container input {
  width: 100%;
  color: #BFBFBF;
  border: 1px solid #BFBFBF;
  border-radius: 6px;
  font-family: 'Roboto', sans-serif;
  height: 40px;
  font-size: 16px;
}

</style>