<template>
  <div class="container">
    <div class="page-main-mw p-4 container-form">
      <div class="change-password-form">
        <p class="header-text">Welkom!</p>
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
            <div>
              <div class="radio-button-container">
                <label>Houd me ingelogd
                  <input type="checkbox">
                  <span class="checkmark"></span>
                </label>
              </div>
              <p class="forgot-password" @click="forgotPassword()">Wachtwoord vergeten?</p>
            </div>
          </div>
        </div>
        <div class="submit-button">
          <p class="wrongLogin">{{ validationText }}</p>
          <button class="bg-primary-500 text-neutral-50 font-semibold hover:bg-primary-700 px-5 py-2.5
          text-sm leading-5 rounded-lg w-fit submit-button" @click="submitButton()">Log in
          </button>
        </div>
      </div>
    </div>
    <div class="login-picture">
      <img src="../../../assets/picture_login.svg" alt="login-picture"/></div>
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
}

img {
  width: 100vw;
  height: 92vh;
}

.change-password-form {
  margin-top: 20%;
  margin-left: 30%;
  width: 400px;
}

.container-form {
  float: left;
  width: 40%;
  order: 1;
}

.login-picture {
  position: relative;
  float: right;
  width: 60%;
  margin: auto;
  order: 2;
}

.header-text {
  font-weight: bold;
  font-size: 28px;
  margin: 3%;
  min-height: 50px;
}

.inputfield-mail {
  margin: 3% 3% 9%;
}

.inputfield-wachtwoord {
  margin: 3% 3% 9%;
}

.submit-button {
  width: 100%;
}

.forgot-password {
  margin: 1%;
  float: right;
  font-weight: bold;
  color: var(--primary-500);
  font-size: 14px;
  order: 2;
}

.forgot-password:hover {
  color: var(--primary-800);
}

.wrongLogin {
  color: red;
  text-align: center;
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
  color: var(--neutral-200);
  border: 1px solid var(--neutral-200);
  border-radius: 6px;
  font-family: 'Roboto', sans-serif;
  height: 40px;
  font-size: 16px;
}

.radio-button-container {
  margin: 1%;
  float: left;
  font-size: 14px;
  color: var(--neutral-50);
  order: 1;
  display: block;
  position: relative;
  padding-left: 35px;
  cursor: pointer;
}

/* Hide the browser's default checkbox */
.radio-button-container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute;
  top: 0;
  left: 0;
  height: 25px;
  width: 25px;
  background-color: var(--neutral-50);
  border: 1px solid var(--neutral-300);;
  border-radius: 25%;
}

/* On mouse-over, add a grey background color */
.radio-button-container:hover input ~ .checkmark {
  background-color: var(--primary-300);
}

/* When the checkbox is checked, add a background */
.radio-button-container input:checked ~ .checkmark {
  background-color: var(--primary-500);
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

/* Show the checkmark when checked */
.radio-button-container input:checked ~ .checkmark:after {
  display: block;
}

/* Style the checkmark/indicator */
.radio-button-container .checkmark:after {
  left: 9px;
  top: 5px;
  width: 5px;
  height: 10px;
  border: solid var(--neutral-50);
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

</style>