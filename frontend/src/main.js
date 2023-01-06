import {createApp} from 'vue'
import {router} from './router/index';
import './assets/css/output.css'
import App from './App.vue'
import PrimaryButton from "./components/Common/PrimaryButton.vue";
import SecondaryButton from "./components/Common/SecondaryButton.vue";
import TextField from "./components/Common/TextField.vue";
import NavBar from "./components/Common/NavBar.vue";
import {library} from '@fortawesome/fontawesome-svg-core'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import './PrototypeExtensions/Array+random.js';
import 'flowbite';

// temporarily import all because single import won't work
import {faBars, faCheck, faChevronRight, faChevronLeft, faEnvelope, faUsers, faWindowMinimize, faXmark, faCircleXmark, faMagnifyingGlass, faBoxArchive, faShareNodes, faUser, faRightFromBracket, faFile, faCalendarDays, faLightbulb, faClock, faCalendarWeek, faTrashCan, faStar, faPen, faFileImage, faPaste, faPlus, faPenToSquare, faArrowDown, faImage, faPanorama} from '@fortawesome/free-solid-svg-icons'
import HelpTip from "./components/Common/HelpTip.vue";

library.add(faUsers, faEnvelope, faXmark, faCheck, faWindowMinimize, faBars, faShareNodes, faChevronRight, faChevronLeft, faMagnifyingGlass, faBoxArchive, faCircleXmark, faUser, faRightFromBracket, faFile, faCalendarDays, faLightbulb, faClock, faCalendarWeek, faTrashCan, faStar, faPen, faFileImage, faPaste, faPlus, faPenToSquare, faArrowDown, faImage, faPanorama);

// `Chainable` list of global components.
const app = createApp(App).use(router)
    .component('PrimaryButton', PrimaryButton)
    .component('SecondaryButton', SecondaryButton)
    .component('TextField', TextField)
    .component('NavBar', NavBar)
    .component('font-awesome-icon', FontAwesomeIcon)
    .component('HelpTip', HelpTip)

app.mount('#app');
