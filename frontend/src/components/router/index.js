import {createRouter, createWebHashHistory} from "vue-router";

import HelloWorld from "../Scenes/Welcome/WelcomeView.vue";

import ProjectOverview from "../Scenes/Project/ProjectOverview.vue";
import LogIn from "../Scenes/Authentication/LogIn.vue";
import ForgotPassword from "../Scenes/Authentication/ForgotPassword.vue";
import ProjectList from "../Scenes/Project/ProjectList.vue";
import ChangePassword from "../Scenes/Authentication/ChangePassword.vue";
import AdminPanel from "../Scenes/Adminpanel/CustomerList.vue";
import { info } from "autoprefixer";
import SpecialistHourRegistrationOverview
    from "../Scenes/SpecialistHourRegistration/SpecialistHourRegistrationOverview.vue";

// Profile components
import Profile from "../Scenes/Profile/Profile.vue";
import PersonalInfo from "../Scenes/Profile/PersonalInfo.vue";
import AvailableHours from "../Scenes/Profile/WorkingHours.vue";
import Skills from "../Scenes/Profile/Skills.vue";
import SkillsOverview from "../Scenes/Profile/SkillsOverview.vue";
import SkillsForm from "../Scenes/Profile/SkillsForm.vue";
import Resume from "../Scenes/Profile/Resume.vue";
import CustomerList from "../Scenes/Adminpanel/CustomerList.vue";
import EmployeeList from "../Scenes/Adminpanel/EmployeeList.vue";


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
                path: "skills",
                component: Skills,
                children: [
                    {
                        path: '/profile/skills',
                        redirect: '/profile/skills/overview', // default child path
                      },
                    {
                        path: 'overview',
                        component: SkillsOverview,
                    },
                    {
                        path: 'edit',
                        component: SkillsForm,
                    },
                ]
            },
            {
                path: 'resume',
                component: Resume,
            },
        ]
    },
    {
        path: '/adminpanel',
        name: "Admin panel",
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
            }
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