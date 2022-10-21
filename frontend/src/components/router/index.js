import {createRouter, createWebHashHistory} from "vue-router";

import HelloWorld from "../HelloWorld.vue";
import Profile from "../Profile.vue";
import ProjectOverview from "../ProjectOverview.vue";
import LogIn from "../LogIn.vue";
import ForgotPassword from "../ForgotPassword.vue";
import ProjectList from "../ProjectList.vue";

const routes = [
    // { path: '/:pathMatch(.*)*', name: 'NotFound', component: UnknownRoute },
    {path: '/', redirect: 'home'},
    {path: '/home', component: HelloWorld},
    {path: '/login', component: LogIn},
    {path: '/login/forgotpassword', component: ForgotPassword},
    {
        // path: "/profile/:id",
        path: "/profile",
        name: "profile",
        // IMPORTANT - UserParent contains `<router-view></router-view>`
        component: Profile,
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