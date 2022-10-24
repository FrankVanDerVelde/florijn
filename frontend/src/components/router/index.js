import {createRouter, createWebHashHistory} from "vue-router";

import HelloWorld from "../HelloWorld.vue";

import ProjectOverview from "../ProjectOverview.vue";
import LogIn from "../LogIn.vue";
import ForgotPassword from "../ForgotPassword.vue";
import ProjectList from "../ProjectList.vue";
import ChangePassword from "../ChangePassword.vue";
import { info } from "autoprefixer";

// Profile components
import Profile from "../Profile.vue";
import PersonalInfo from "../ProfilePage/PersonalInfo.vue";
import AvailableHours from "../ProfilePage/AvailableHours.vue";
import Skills from "../ProfilePage/Skills.vue";
import Resume from "../ProfilePage/Resume.vue";

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
    }
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
});