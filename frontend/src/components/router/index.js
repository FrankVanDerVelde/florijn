import { createRouter, createWebHashHistory } from "vue-router";

import HelloWorld from "../HelloWorld.vue";
import Profile from "../Profile.vue";
import ProjectOverview from "../ProjectOverview.vue";

const routes = [
    // { path: '/:pathMatch(.*)*', name: 'NotFound', component: UnknownRoute },
    { path: '/', redirect: 'home'},
    { path: '/home', component: HelloWorld },
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
    }
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
});