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
import DangerZoneRow from "../../../src/components/Scenes/Project/DangerZoneRow.vue";
import TransferOwnershipModal from "../../../src/components/Scenes/Project/Modals/TransferOwnershipModal.vue";
import 'intersection-observer';

export let projectRepository
export let userRepository;

beforeEach(async () => {
    loadRepos();
});

export function loadRepos() {
    if (userRepository == null) userRepository = new InMemoryUserRepo();
    userRepository.save(new Client(5, 'ING', '', 'contact@ing.nl'));
    userRepository.save(new Client(6, 'KPN', '', 'something@kpn.nl'));
    userRepository.save(new Admin(7, 'Admin', '', 'admin@florijn.nl'));
    userRepository.save(new Specialist(8, 'Bobbi', 'Eden', '', 'admin@florijn.nl'));

    if (projectRepository == null) projectRepository = new InMemoryProjectRepo(null, false, userRepository.findById(7));
    projectRepository.save(new Project(1, 'Hello World', 'I am a disney princess', userRepository.findById(5)));
}

export async function createWrapper(user = userRepository.findById(7)) {
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
            stubs: ['router-link', 'Asset', 'ProjectLogo', 'font-awesome-icon'],
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

describe('can see transfer button', () => {
    testButtonPermissionsWithUser(null, false);
    testButtonPermissionsWithUser(7, true);
    testButtonPermissionsWithUser(5, false);
    testButtonPermissionsWithUser(6, false);
});

describe('test popup functionality', () => {
    it('do elements exist', async () => {
        projectRepository = new InMemoryProjectRepo(null, false, userRepository.findById(7));
        loadRepos();

        const wrapper = await createWrapper();
        await wrapper.vm.$nextTick();

        // getting the danger zone row for transferring users.
        const dangerZoneRow = wrapper.findComponent({name: DangerZoneRow.name, props: {id: 'dz-transfer'}});
        expect(dangerZoneRow.exists()).toBe(true);
        expect(dangerZoneRow.isVisible()).toBe(true);

        // getting the modal for transferring users.
        const modal = wrapper.findComponent(TransferOwnershipModal);
        expect(modal.exists()).toBe(true);
        expect(modal.vm.open).toBe(false);
    });
    it('can open and close popup', async () => {
        const wrapper = await createWrapper();

        // getting the modal and making sure it is closed.
        const modal = wrapper.findComponent(TransferOwnershipModal);
        expect(modal.vm.open).toBe(false);

        // clicking the button that opens the modal.
        const dangerZoneRow = wrapper.findComponent({name: DangerZoneRow.name, props: {id: 'dz-transfer'}});
        await dangerZoneRow.find('button').trigger('click');
        await wrapper.vm.$nextTick();

        // making sure it opened
        expect(modal.vm.open).toBe(true);

        // clicking the button that closes the modal.
        let tmodal = modal.findComponent({name: 'Modal'}).findComponent({name: 'DialogPanel'});
        const modalCloseButton = tmodal.find('#btn-cancel');

        expect(modalCloseButton.exists()).toBe(true);

        await modalCloseButton.trigger('click');
        await wrapper.vm.$nextTick();

        expect(modal.vm.open).toBe(false);
    });
    it('check v model functionality', async () => {
        const wrapper = await createWrapper();
        await wrapper.vm.$nextTick();

        const modal = wrapper.findComponent(TransferOwnershipModal);
        modal.vm.open = true;
        await wrapper.vm.$nextTick();

        let content = modal.findComponent({name: 'Modal'}).findComponent({name: 'DialogPanel'});
        let submitButton = content.find('#btn-submit');
        expect(getButtonDisabled(submitButton)).toBeDefined();

        const currentClient = wrapper.vm.project.client; // ING

        // selecting a customer (KPN) and making sure the button is still disabled.
        let clientNameOnPage = content.find('[type="button"] span span')
        modal.vm.project.client = userRepository.findById(6);
        await wrapper.vm.$nextTick();
        expect(clientNameOnPage.text()).toBe(modal.vm.project.client.name);
        expect(getButtonDisabled(submitButton)).toBeDefined();

        let confirmInput = content.find('#confirm-input');

        // entering a random confirmation title and making sure the button is still disabled.
        await enterConfirmTitle(wrapper, confirmInput, 'something random here');
        expect(getButtonDisabled(submitButton)).toBeDefined();

        // entering the correct confirmation title and making sure the button is enabled.
        await enterConfirmTitle(wrapper, confirmInput, wrapper.vm.project.title);
        expect(getButtonDisabled(submitButton)).toBeUndefined();

        // only entering the right title, and the current client, and making sure the button is disabled.
        modal.vm.project.client = currentClient;
        await wrapper.vm.$nextTick();
        expect(getButtonDisabled(submitButton)).toBeDefined();
    });
    it('check transfer functionality', async () => {
        const wrapper = await createWrapper();
        await wrapper.vm.$nextTick();

        projectRepository.userRepo = userRepository;

        const modal = wrapper.findComponent(TransferOwnershipModal);
        modal.vm.open = true;
        await wrapper.vm.$nextTick();

        const projectId = wrapper.vm.projectId;

        let content = modal.findComponent({name: 'Modal'}).findComponent({name: 'DialogPanel'});
        let submitButton = content.find('#btn-submit');

        // selecting the right values.
        modal.vm.project.client = userRepository.findById(6);
        modal.vm.projectTitle = wrapper.vm.project.title;
        await wrapper.vm.$nextTick();

        // making sure the button is enabled.
        expect(getButtonDisabled(submitButton)).toBeUndefined();

        let spyInstance = jest.spyOn(modal.vm.$router, 'push');

        await submitButton.trigger('click');
        await wrapper.vm.$nextTick();

        expect(spyInstance).toHaveBeenCalled();

        const project = projectRepository.fetchProjectById(projectId);
        expect(project.client.id).toBe(6);
    });
});

async function enterConfirmTitle(wrapper, input, value) {
    await input.setValue(value);
    await wrapper.vm.$nextTick();
}

function getButtonDisabled(button) {
    return button.attributes().disabled;
}

function testButtonPermissionsWithUser(id, shouldHavePermission) {
    it(`user with id ${id} should${shouldHavePermission ? '' : ' not'} have permission`, async () => {
        // loading the repository, with the user as requester (to check access)
        let user = userRepository.findById(id) ?? null;
        projectRepository = new InMemoryProjectRepo(null, false, user);
        loadRepos();

        // loading the component
        let wrapper = await createWrapper(user);
        await wrapper.vm.$nextTick();

        const transferDangerZone = wrapper.find('#dz-transfer');
        expect(transferDangerZone.exists()).toBe(shouldHavePermission);
    });
}

function testPermissionsWithUser(id, shouldHavePermission) {
    it(`user with id ${id} should${shouldHavePermission ? '' : ' not'} have permission`, async () => {
        // loading the repository, with the user as requester (to check access)
        let user = userRepository.findById(id) ?? null;
        projectRepository = new InMemoryProjectRepo(null, false, user);
        loadRepos();

        // loading the component
        let wrapper = await createWrapper(user);
        const onRouterPushSpy = jest.spyOn(wrapper.vm.$router, 'push');

        // checking if the user should (not) be null
        await wrapper.vm.$nextTick();
        if (id != null) expect(wrapper.vm.user).not.toBeNull();
        else expect(wrapper.vm.user).toBeFalsy();

        // checking if the user was meant to be redirected (no permission)
        await wrapper.vm.$nextTick();
        expect(onRouterPushSpy).toHaveBeenCalledTimes(shouldHavePermission ? 0 : 1);
    });
}