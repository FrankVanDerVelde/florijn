import {createRouter, createWebHashHistory} from "vue-router";

import HelloWorld from "../components/Scenes/Welcome/WelcomeView.vue";

import ProjectOverview from "../components/Scenes/Project/ProjectOverview.vue";
import LogIn from "../components/Scenes/Authentication/LogIn.vue";
import ForgotPassword from "../components/Scenes/Authentication/ForgotPassword.vue";
import ProjectList from "../components/Scenes/Project/ProjectList.vue";
import ChangePassword from "../components/Scenes/Authentication/ChangePassword.vue";
import AdminPanel from "../components/Scenes/Adminpanel/CustomerList.vue";
import { info } from "autoprefixer";
import SpecialistHourRegistrationOverview
    from "../components/Scenes/SpecialistHourRegistration/SpecialistHourRegistrationOverview.vue";

// Profile components
import Profile from "../components/Scenes/Profile/Profile.vue";
import PersonalInfo from "../components/Scenes/Profile/PersonalInfo.vue";
import AvailableHours from "../components/Scenes/Profile/WorkingHours.vue";
import Skills from "../components/Scenes/Profile/Skills.vue";
import SkillsOverview from "../components/Scenes/Profile/SkillsOverview.vue";
import SkillsForm from "../components/Scenes/Profile/SkillsForm.vue";
import Resume from "../components/Scenes/Profile/Resume.vue";
import CustomerList from "../components/Scenes/Adminpanel/CustomerList.vue";
import EmployeeList from "../components/Scenes/Adminpanel/EmployeeList.vue";


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
                path: "/profile/hour-registration",
                name: "hour-registration",
                component: SpecialistHourRegistrationOverview
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
];

export const router = createRouter({
    history: createWebHashHistory(),
    routes
});