import {mount} from '@vue/test-utils'
import PublicProfile from '../../../src/components/Scenes/Profile/PublicProfile.vue'
import Asset from "../../../src/components/Common/Asset.vue";
import {createMemoryHistory, createRouter} from "vue-router";
import LogIn from "../../../src/components/Scenes/Authentication/LogIn";
import {DateService} from "../../../src/Services/DateService";
import InMemoryUserRepo from "../../mockRepos/InMemoryUserRepo";
import InMemoryProjectRepo from "../../mockRepos/InMemoryProjectRepo";
import InMemoryAssetService from "../../../tests/mockRepos/InMemoryAssetService.js";
import InMemorySkillsRepo from "../../../tests/mockRepos/InMemoryAssetService.js";
import InMemoryAvailabilityRepo from "../../mockRepos/InMemoryAvailabilityRepo.js";

describe('PublicProfile', () => {
    let wrapper;
    let userRepository;
    let skillsRepository;
    let projectRepository;

    const router = createRouter({
        history: createMemoryHistory(),
        routes: [{path: '/', component: LogIn}, {path: '/login', component: LogIn}, {path: '/profile/public/:id', component: {default: PublicProfile}}],
    })

    beforeEach(() => {
        userRepository = new InMemoryUserRepo();
        skillsRepository = new InMemorySkillsRepo();
        projectRepository = new InMemoryProjectRepo();

        wrapper = mount(PublicProfile, {
            global: {
                stubs: ['router-link', 'font-awesome-icon', 'Asset'],
                provide: {
                    assetsService: InMemoryAssetService,
                    dateService: new DateService(),
                    userRepository: userRepository,
                    projectRepository: projectRepository,
                    skillsRepository: skillsRepository,
                    availabilityRepository: InMemoryAvailabilityRepo
                },
                plugins: [router]
            }
        });
    })

    it('should show text when no data is found', () => {
        expect(wrapper.find('p.back-text').text()).toEqual('< Terug');

        //check if the error message element exists since availability is empty
        expect(wrapper.find('av-error')).toBeTruthy;
        expect(wrapper.find('pro-error')).toBeTruthy;
        expect(wrapper.find('skill-error')).toBeTruthy;
    })

})
