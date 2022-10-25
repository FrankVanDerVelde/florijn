import {createRouter, createWebHashHistory} from "vue-router";

import HelloWorld from "../Scenes/Welcome/WelcomeView.vue";

import ProjectOverview from "../Scenes/Project/ProjectOverview.vue";
import LogIn from "../Scenes/Authentication/LogIn.vue";
import ForgotPassword from "../Scenes/Authentication/ForgotPassword.vue";
import ProjectList from "../Scenes/Project/ProjectList.vue";
import ChangePassword from "../Scenes/Authentication/ChangePassword.vue";
import { info } from "autoprefixer";
import SpecialistHourRegistrationOverview
    from "../Scenes/SpecialistHourRegistration/SpecialistHourRegistrationOverview.vue";

// Profile components
import Profile from "../Scenes/ProfilePage/Profile.vue";
import PersonalInfo from "../Scenes/ProfilePage/PersonalInfo.vue";
import AvailableHours from "../Scenes/ProfilePage/AvailableHours.vue";
import Skills from "../Scenes/ProfilePage/Skills.vue";
import Resume from "../Scenes/ProfilePage/Resume.vue";


const routes = [
    // { path: '/:pathMatch(.*)*', name: 'NotFound', component: UnknownRoute },
    {path: '/', redirect: 'home'},
    {path: '/home', component: HelloWorld},
    {path: '/login', component: LogIn},
    {path: '/login/forgotpassword', component: ForgotPassword},
    {path: '/login/forgotpassword/cp', component: ChangePassword},
    {
        // path: "/profile/:id",
        path: "/profile",
        name: "profile",
        // IMPORTANT - UserParent contains `<router-view></router-view>`
        component: Profile,
        children: [
            {
                path: '/profile',
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
                path: 'skills',
                component: Skills,
            },
            {
                path: 'resume',
                component: Resume,
            },
        ]
    },
    {
        path: "/project-overview",
        name: "project overview",
        component: ProjectOverview
    }, {
        path: "/project-list",
        name: "project list",
        component: ProjectList
    },
    {
        path: "/hour-registration",
        name: "hour-registration",
        component: SpecialistHourRegistrationOverview
    },

];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
});