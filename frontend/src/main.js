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
import {
    faArrowDown,
    faBars,
    faBarsProgress,
    faBoxArchive,
    faBuilding,
    faCalendarDays,
    faCalendarWeek,
    faCheck,
    faCircleCheck,
    faChevronLeft,
    faChevronRight,
    faCircleXmark,
    faClock,
    faEnvelope,
    faFaceGrinSquint,
    faFile,
    faFileImage,
    faLightbulb,
    faMagnifyingGlass,
    faPaste,
    faPen,
    faPenToSquare,
    faPlus,
    faRightFromBracket,
    faShareNodes,
    faStar,
    faTrashCan,
    faUser,
    faUserPlus,
    faUsers,
    faWindowMinimize,
    faXmark,
    faImage,
    faPanorama,
    faEye
} from '@fortawesome/free-solid-svg-icons'
import HelpTip from "./components/Common/HelpTip.vue";

library.add(
    faUsers, faEnvelope, faXmark, faCheck, faCircleCheck, faWindowMinimize, faBars, faShareNodes, faChevronRight, faChevronLeft, faMagnifyingGlass,
    faBoxArchive, faCircleXmark, faUser, faRightFromBracket, faFile, faCalendarDays, faLightbulb, faClock, faCalendarWeek, faTrashCan,
    faStar, faPen, faFileImage, faPaste, faPlus, faPenToSquare, faArrowDown, faUserPlus, faBuilding, faBarsProgress, faFaceGrinSquint,
    faImage, faPanorama, faEye
);

// `Chainable` list of global components.
const app = createApp(App).use(router)
    .component('PrimaryButton', PrimaryButton)
    .component('SecondaryButton', SecondaryButton)
    .component('TextField', TextField)
    .component('NavBar', NavBar)
    .component('font-awesome-icon', FontAwesomeIcon)
    .component('HelpTip', HelpTip)

app.mount('#app');
