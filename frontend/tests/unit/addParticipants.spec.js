import { mount, shallowMount } from '@vue/test-utils'
import AddParticipants from "../../src/components/Scenes/AddParticipants/AddParticipants.vue";
import InMemoryEntitiesService from '../mockRepos/InMemoryEntitiesService.js';
import Participant from '../../src/components/models/Participant.js';
import {Project} from "../../src/components/models/Project";
import SkillsGroup from "../../src/components/Scenes/Profile/SkillsGroup.vue";

let wrapper;
let inMemoryUserRepo;
let inMemorySkillsRepo;
let inMemoryProjectRepo;

beforeEach(function () {

    inMemoryUserRepo = new InMemoryEntitiesService(10000, Participant.createDummy);
    inMemorySkillsRepo = new InMemoryEntitiesService(10000, SkillsGroup.createDummy);
    inMemoryProjectRepo = new InMemoryEntitiesService(10000, Project.createDummy);

    wrapper = mount(AddParticipants, {
        propsData: {},
        props: {
            project: Project.createDummy()
        },

        global: {
            provide: {
                "userRepository": inMemoryUserRepo,
                "skillsRepository": inMemorySkillsRepo,
                "projectRepository": inMemoryProjectRepo
            },
            stubs: ['FontAwesomeIcon', 'Asset']
        }
    });


})

test('Proper initalization of the user service', function () {
    expect(inMemoryUserRepo.entities.length).toBeGreaterThan(0);
})

test('Proper initalization of the project service', function () {
    expect(inMemoryProjectRepo.entities.length).toBeGreaterThan(0);
})

test('findAll returns all projects and users', function () {
    let users = inMemoryProjectRepo.findAll();
    expect(users)
        .toStrictEqual(inMemoryProjectRepo.entities);

    let projects = inMemoryProjectRepo.findAll();
    expect(projects)
        .toStrictEqual(inMemoryProjectRepo.entities);
})

test('renders properly', () => {
    expect(wrapper.find('h2').text()).toBe('Huidige deelnemers');
    console.log(wrapper.html());
    expect(wrapper.find('h2').text()).toBe('Deelnemers toevoegen');
})


