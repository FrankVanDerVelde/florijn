import {mount} from "@vue/test-utils";
import CreateProject from "../../../src/components/Scenes/Project/Scenes/CreateProject.vue";
import InMemoryProjectRepo from "../../mockRepos/InMemoryProjectRepo";
import InMemoryUserRepo from "../../mockRepos/InMemoryUserRepo";
import {StringService} from "../../../src/Services/StringService";
import ProjectLayout from "../../../src/components/Scenes/Project/Scenes/ProjectLayout.vue";
import Client from "../../../src/components/models/user/Client";
import Admin from "../../../src/components/models/user/Admin";
import {createMemoryHistory, createRouter} from "vue-router";

let wrapper;

const projectRepository = new InMemoryProjectRepo(null, false);
const userRepository = new InMemoryUserRepo();
beforeEach(async () => {
    userRepository.save(new Client(5, 'ING', '', 'contact@ing.nl'));

    const router = createRouter({
        history: createMemoryHistory(),
        routes: [],
    })

    wrapper = await mount(CreateProject, {

        props: {
            newProject: true,
        },
        computed: {
            user() {
                return new Admin(1, "Bart", "Groene", "", "bart@test.com")
            },
        },
        global: {
            stubs: ['router-link', 'Asset'],
            provide: {
                projectRepository: projectRepository,
                userRepository: userRepository,
                stringService: new StringService(),
            },
            plugins: [router],
        }
    })
});

describe('v-model testing', () => {
    it('can set title', async () => {
        await testInput('#title-input', 'test title', 'title', '.title');
    });
    it('can set description', async () => {
        await testInput('#description-input', 'test description', 'description', '.description');
    });
    it('can save project', async () => {
        await wrapper.vm.$nextTick();
        expect(projectRepository.entities.length).toBe(0);

        // inserting the data.
        wrapper.find('#title-input').setValue('test title');
        wrapper.find('#description-input').setValue('test description');
        wrapper.vm.project.client = userRepository.entities[0];

        await wrapper.vm.$nextTick();

        const onRouterPushSpy = jest.spyOn(wrapper.vm.$router, 'push');

        // submitting the form and making sure there are no errors.
        wrapper.find('form').trigger('submit.prevent');
        expect(wrapper.vm.errors).toStrictEqual({});

        expect(projectRepository.entities.length).toBe(1);
        const project = projectRepository.entities[0];

        // validating the inserted data with the entered data.
        expect(project.title).toBe('test title');
        expect(project.description).toBe('test description');
        expect(project.client).toBe(wrapper.vm.project.client.id);

        // making sure we were redirected to the right page.
        await wrapper.vm.$nextTick();
        expect(onRouterPushSpy).toHaveBeenCalledWith(`/projects/${project.id}`);
    });
});

async function testInput(inputId, value, property, previewId) {
    await wrapper.vm.$nextTick();
    const layout = wrapper.findComponent(ProjectLayout);

    const descriptionInput = wrapper.find(inputId);
    expect(wrapper.vm[property]).toEqual('');
    descriptionInput.setValue(value);
    expect(wrapper.vm[property]).toStrictEqual(value);
    await wrapper.vm.$nextTick();
    expect(layout.find(previewId).text()).toStrictEqual(value);
}