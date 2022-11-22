import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";

import WelcomeView from "../components/Scenes/Welcome/WelcomeView.vue";
import WelcomeAdminView from "../components/Scenes/Welcome/WelcomeAdminView.vue";
import WelcomeSpecialistView from "../components/Scenes/Welcome/WelcomeSpecialistView.vue";
import WelcomeClientView from "../components/Scenes/Welcome/WelcomeClientView.vue";

import ProjectLayout from "../components/Scenes/Project/Scenes/ProjectLayout.vue";
import LogIn from "../components/Scenes/Authentication/LogIn.vue";
import ForgotPassword from "../components/Scenes/Authentication/ForgotPassword.vue";
import ProjectList from "../components/Scenes/Project/ProjectList.vue";
import ChangePassword from "../components/Scenes/Authentication/ChangePassword.vue";
import AdminPanel from "../components/Scenes/Adminpanel/Adminpanel.vue";
import AddClient from "../components/Scenes/Adminpanel/AddClient.vue";
import CustomerList from "../components/Scenes/Adminpanel/CustomerList.vue";
import EmployeeList from "../components/Scenes/Adminpanel/EmployeeList.vue";
import SpecialistHourRegistrationOverview from "../components/Scenes/SpecialistHourRegistration/SpecialistHourRegistrationOverview.vue";

// Profile components
import Profile from "../components/Scenes/Profile/Profile.vue";
import PersonalInfo from "../components/Scenes/Profile/PersonalInfo.vue";
import AvailableHours from "../components/Scenes/Profile/WorkingHours.vue";
import SkillsOverview from "../components/Scenes/Profile/SkillsOverview.vue";
import Resume from "../components/Scenes/Profile/Resume.vue";
import AddParticipants from "../components/Scenes/AddParticipants/AddParticipants.vue";
import ProjectOverview from "../components/Scenes/Project/Scenes/ProjectOverview.vue";


const routes = [
    // { path: '/:pathMatch(.*)*', name: 'NotFound', component: UnknownRoute },
    {path: '/', redirect: 'home'},
    {path: '/home', component: WelcomeView},
    {path: '/admin/home', component: WelcomeAdminView},
    {path: '/specialist/home', component: WelcomeSpecialistView},
    {path: '/client/home', component: WelcomeClientView},
    {path: '/login', component: LogIn},
    {path: '/login/forgotpassword', component: ForgotPassword},
    {path: '/login/forgotpassword/cp', component: ChangePassword},
    {
        path: "/profile",
        component: Profile,
        children: [
            {
                path: '',
                name: "profile",
                redirect: '/profile/personal-info', // default child path
            },
            {
                path: 'personal-info',
                component: PersonalInfo,
            },
            {
                path: 'available-hours',
                component: AvailableHours,
            },
            {
                path: "hour-registration",
                name: "hour-registration",
                component: SpecialistHourRegistrationOverview
            },
            {
                path: "skills",
                component: SkillsOverview,
            },
            {
                path: 'resume',
                component: Resume,
            },
        ]
    },
    {
        path: '/adminpanel',
        name: "adminpanel",
        component: AdminPanel,
        children: [
            {
                path: "/adminpanel",
                redirect: "/adminpanel/customer-list",
            },
            {
                path: "customer-list",
                component: CustomerList,
            },
            {
                path: "employee-list",
                component: EmployeeList,
            },
        ]
    },
    {
        path: "/adminpanel/add-client",
        component: AddClient,
    },
    {
        path: "/projects",
        name: "projects",
        component: ProjectList
    },
    {
        path: "/projects/:projectId(\\d+)",
        component: ProjectLayout,
        props: true,
        children: [
            {
                path: '',
                name: "project-overview",
                component: ProjectOverview
            }, {
                path: "participants",
                name: "project-participants",
                component: AddParticipants,
            }
        ]
    }
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
});