import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import PrimaryButton from "./components/Common/PrimaryButton.vue";

// `Chainable` list of global components.
let app = createApp(App)
    .component('PrimaryButton', PrimaryButton)

app.mount('#app')
