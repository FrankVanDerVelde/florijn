import {createApp} from 'vue'
import {router} from './components/router/index';
import './style.css'
import App from './App.vue'
import PrimaryButton from "./components/Common/PrimaryButton.vue";
import SecondaryButton from "./components/Common/SecondaryButton.vue";
import TextField from "./components/Common/TextField.vue";
import NavBar from "./components/Common/NavBar.vue";
import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

// temporarily import all because single import won't work
import {faBars, faCheck, faChevronRight, faEnvelope, faUsers, faWindowMinimize, faXmark, faUser, faFile} from '@fortawesome/free-solid-svg-icons'

library.add(faUsers, faEnvelope, faXmark, faCheck, faWindowMinimize, faBars, faChevronRight, faUser, faFile);

// `Chainable` list of global components.
const app = createApp(App).use(router)
    .component('PrimaryButton', PrimaryButton)
    .component('SecondaryButton', SecondaryButton)
    .component('TextField', TextField)
    .component('NavBar', NavBar)
    .component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app')
