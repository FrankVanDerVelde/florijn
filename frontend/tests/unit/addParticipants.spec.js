import { mount, shallowMount } from '@vue/test-utils'
import AddParticipants from "../../src/components/Scenes/AddParticipants/AddParticipants.vue";
import InMemoryEntitiesService from '../mockRepos/InMemoryEntitiesService.js';
import Specialist from '../../src/components/models/user/Specialist.js';
import {Project} from "../../src/components/models/Project";
import SkillsGroup from "../../src/components/Scenes/Profile/SkillsGroup.vue";
import InMemoryProjectRepo from "../mockRepos/InMemoryProjectRepo";
import InMemoryUserRepo from "../mockRepos/InMemoryUserRepo";
import InMemorySkillsRepo from "../mockRepos/InMemorySkillsRepo";
import participant from "../../src/components/Scenes/Project/Participant.vue";

let wrapper;
let inMemoryUserRepo;
let inMemorySkillsRepo;
let inMemoryProjectRepo;
let specialist1;

beforeEach(function () {

    inMemoryUserRepo = new InMemoryUserRepo();
    inMemorySkillsRepo = new InMemorySkillsRepo();
    inMemoryProjectRepo = new InMemoryProjectRepo(null, false);

    inMemoryUserRepo.save(new Specialist(1, "Bart", "Groene", "", "test@test.com"));

    wrapper = mount(AddParticipants, {
        propsData: {},
        props: {
            project: Project.createSample(1000)
        },

        global: {
            provide: {
                "userRepository": inMemoryUserRepo,
                "skillsRepository": inMemorySkillsRepo,
                "projectRepository": inMemoryProjectRepo,

            },
            stubs: ['FontAwesomeIcon', 'Asset', 'router-link']
        }
    });


})

describe('Correct Initialisation of the main page', () => {
    it('should import correct users', function () {
        expect(inMemoryUserRepo.entities.length).toBeGreaterThan(0);
    });
    it('should render title properly', function () {
        expect(wrapper.find('h2').text()).toBe('Huidige deelnemers');
        console.log(wrapper.html());
    });
   it("should render the correct amount of users", function () {
        expect(wrapper.findAll('.user').length).toBe(inMemoryUserRepo.entities.length);
    });
    it('should render all skills correctly', function () {
        expect(wrapper.findAll('.skill').length).toBe(inMemorySkillsRepo.entities.length);
    });
    it('should render all users correctly', function () {
        expect(wrapper.findAll('.user').length).toBe(inMemoryUserRepo.entities.length);
    })
});

