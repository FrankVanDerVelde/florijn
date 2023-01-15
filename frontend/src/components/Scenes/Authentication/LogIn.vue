<template>
  <div class="grid grid-cols-12 absolute w-full h-full top-0 right-0 ">
    <div class="flex justify-end col-span-6 row-start-1 h-full form-column">
      <div class="flex w-full justify-center items-center h-full max-width">
        <form @submit.prevent="submitButton()" class="w-140">
          <h1 class="w-3 font-bold text-[20px] mb-[24px]">Welkom!</h1>
          <div class="inputfield">
            <div class="input-container">
              <label class="mb-[12px]">Emailadres</label>
              <input v-model="email" type="email" required class="pl-[7px] w-80">
            </div>
          </div>
          <div class="inputfield">
            <div class="input-container">
              <label class="mb-[12px]">Wachtwoord</label>
              <input v-model="password" required type="password" class="pl-[7px] w-80">
            </div>
          </div>
          <p class="text-center w-80 min-h-[50px] text-app_red-500">&nbsp; {{ validationText }}</p>
          <div class="submit-button">
            <button class="bg-primary-500 text-neutral-50 font-semibold hover:bg-primary-700 py-2.5
          text-sm leading-5 rounded-lg w-80">Log in
            </button>
          </div>
        </form>
      </div>
    </div>
    <div class="col-span-6 row-start-1 col-start-7 h-full bg-primary-300 hide-login-picture">
      <div class="flex justify-center items-center h-full max-width ">
        <img src="/src/assets/picture-login.svg" class="" alt="login-picture"/>
      </div>
    </div>
  </div>
</template>

<script>
import CryptoJS from 'crypto-js'

export default {
  name: "LogIn.vue",
  inject: ['authenticationRepository', 'storedTokenRepository'],
  data() {
    return {
      email: '',
      password: '',
      validationText: '',
    };
  },
  methods: {
    async submitButton() {
      try {
        await this.authenticationRepository.authenticateWithCredentials(this.email.trim(), this.hashMethod(this.password));
        this.pushToHome();
      } catch (e) {
        console.error(e)
        this.validationText = 'De inloggegevens zijn onjuist ingevuld! Probeer het nogmaals';
        this.password = '';
      }
    },
    hashMethod(password){
      if (password != null){
        const salt = "mixer";
        const key = CryptoJS.PBKDF2(password, salt, { keySize: 512/32, iterations: 1000 });
        return key.toString(CryptoJS.enc.Hex);
      }
    },
    pushToHome() {
      this.$router.push({name: "home"});
    }
  },
}
</script>

<style scoped>

.max-width {
  max-width: 720px;
}

.grid img {
  padding-top: 80px;
  max-width: 600px;
  width: 100%;
}

@media screen and (max-width: 768px) {
  .hide-login-picture {
    display: none;
  }

  .form-column {
    grid-column: span 12;
  }
}

.inputfield {
  margin: 3% 3% 9%;
}

.submit-button {
  justify-content: center;
  align-items: center;
  margin: 0.5vh;
  text-align: center;
}

.forgot-password {
  margin: 3% 0 3% 3%;
  text-align: center;
  font-weight: bold;
  font-size: 12px;
}

.input-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.input-container label {
  font-weight: 600;
  color: var(--neutral-300);
  font-size: 14px
}

.input-container input {
  color: black;
  border: 1px solid var(--neutral-300);
  border-radius: 6px;
  height: 40px;
  font-size: 16px;
}

/* Hide the browser's default checkbox */
.radio-button-container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  display: none;
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
