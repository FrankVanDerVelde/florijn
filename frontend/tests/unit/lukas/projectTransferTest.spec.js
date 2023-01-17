import InMemoryProjectRepo from "../../mockRepos/InMemoryProjectRepo";
import InMemoryUserRepo from "../../mockRepos/InMemoryUserRepo";
import Client from "../../../src/components/models/user/Client";
import {createMemoryHistory, createRouter} from "vue-router";
import {mount} from "@vue/test-utils";
import CreateProject from "../../../src/components/Scenes/Project/Scenes/CreateProject.vue";
import Admin from "../../../src/components/models/user/Admin";
import {StringService} from "../../../src/Services/StringService";
import {Project} from "../../../src/components/models/Project";
import Specialist from "../../../src/components/models/user/Specialist";

let projectRepository = new InMemoryProjectRepo(null, false);
const userRepository = new InMemoryUserRepo();

beforeEach(async () => {
    loadRepos();
});

function loadRepos() {
    userRepository.save(new Client(5, 'ING', '', 'contact@ing.nl'));
    userRepository.save(new Client(6, 'KPN', '', 'something@kpn.nl'));
    userRepository.save(new Admin(7, 'Admin', '', 'admin@florijn.nl'));
    userRepository.save(new Specialist(8, 'Bobbi', 'Eden', '', 'admin@florijn.nl'));
    projectRepository.save(new Project(1, 'Hello World', 'I am a disney princess', userRepository.entities[0]));
}

async function createWrapper(user) {
    const router = createRouter({
        history: createMemoryHistory(),
        routes: [],
    })

    const $router = {
        push: jest.fn()
    }

    return mount(CreateProject, {
        props: {
            projectId: 1,
        },
        computed: {
            user: () => user,
        },
        global: {
            mocks: {
              $router,
            },
            stubs: ['router-link', 'Asset'],
            provide: {
                projectRepository: projectRepository,
                userRepository: userRepository,
                stringService: new StringService(),
            },
            plugins: [router],
        }
    })
}


describe('test permissions', () => {

    testPermissionsWithUser(null, false);
    testPermissionsWithUser(7, true);
    testPermissionsWithUser(5, true);
    testPermissionsWithUser(6, false);

});

function testPermissionsWithUser(id, shouldHavePermission) {
    it(`user with id ${id} should ${shouldHavePermission ? '' : 'not'} have permission`, async () => {
        // loading the repository, with the user as requester (to check access)
        let user = userRepository.findById(id);
        projectRepository = new InMemoryProjectRepo(null, false, user);
        loadRepos();

        // loading the component
        let wrapper = await createWrapper(user);
        const onRouterPushSpy = jest.spyOn(wrapper.vm.$router, 'push');

        // checking if the user should (not) be null
        await wrapper.vm.$nextTick();
        if (id != null) expect(wrapper.vm.user).not.toBeNull();
        else expect(wrapper.vm.user).toBeUndefined();

        // checking if the user was meant to be redirected (no permission)
        await wrapper.vm.$nextTick();
        expect(onRouterPushSpy).toHaveBeenCalledTimes(shouldHavePermission ? 0 : 1);
    });
}