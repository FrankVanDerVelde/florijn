import { createApp } from 'vue'
import { router } from './components/router/index';
import './style.css'
import App from './App.vue'
import PrimaryButton from "./components/Common/PrimaryButton.vue";
import SecondaryButton from "./components/Common/SecondaryButton.vue";
import TextField from "./components/Common/TextField.vue";
import NavBar from "./components/Common/NavBar.vue";

// `Chainable` list of global components.
const app = createApp(App).use(router)
    .component('PrimaryButton', PrimaryButton)
    .component('SecondaryButton', SecondaryButton)
    .component('TextField', TextField)
    .component('NavBar', NavBar)

app.mount('#app')
