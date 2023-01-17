import {mount} from "@vue/test-utils";
import ProjectHeader from "../../../src/components/Scenes/Project/ProjectHeader.vue";
import * as projectTransferTest from "./projectTransferTest.spec";
import ArchiveProjectModal from "../../../src/components/Scenes/Project/Modals/ArchiveProjectModal.vue";
import {createMemoryHistory, createRouter} from "vue-router";
import 'intersection-observer';

let projectRepository
let userRepository;

beforeEach(async () => {
    projectTransferTest.loadRepos();
    projectRepository = projectTransferTest.projectRepository;
    userRepository = projectTransferTest.userRepository;
});

describe('is right data being displayed', () => {
    it('is project header info correct', async () => {
        let wrapper = await mount(ProjectHeader, {
            props: {
                project: projectRepository.findById(1),
            },
            global: {
                stubs: ['router-link', 'font-awesome-icon'],
            }
        });

        // making sure the project is not archived and the archive stat is not being displayed
        expect(wrapper.vm.project.archived).toBe(false);
        expect(wrapper.find('#stat-archived').exists()).toBe(false);

        // archiving the project and making sure the stat is being displayed
        wrapper.vm.project.archived = true;
        await wrapper.vm.$nextTick();
        expect(wrapper.find('#stat-archived').exists()).toBe(true);
    });
    it('is project edit page info correct', async () => {
        const wrapper = await projectTransferTest.createWrapper();
        await wrapper.vm.$nextTick();

        // making sure that the right components for unarchived projects are being displayed
        expect(wrapper.vm.project.archived).toBe(false);
        testArchiveComponents(wrapper);

        // archiving the project and making sure the archive components are being displayed
        wrapper.vm.project.archived = true;
        await wrapper.vm.$nextTick();
        testArchiveComponents(wrapper);
    });
});

describe('is archive/unarchive working', () => {
    it('is archive working', async () => {
        await testArchive(true);
    });
    it('is unarchive working', async () => {
        await testArchive(false);
    });
});

async function testArchive(archive) {
    projectRepository.findById(1).archived = !archive;
    const wrapper = await mount(ArchiveProjectModal, {
        props: {
            project: projectRepository.findById(1),
            archive: archive,
        },
        global: {
            stubs: ['router-link', 'font-awesome-icon', 'Asset'],
            mocks: {
                $router: {
                    push: jest.fn()
                }
            },
            provide: {
                projectRepository: projectRepository,
            },
            plugins: [
                createRouter({
                    history: createMemoryHistory(),
                    routes: [],
                })
            ]
        }
    })
    wrapper.vm.open = true;
    await wrapper.vm.$nextTick();

    expect(wrapper.vm.project.archived).toBe(!archive);

    let content = wrapper.findComponent({name: 'Modal'}).findComponent({name: 'DialogPanel'});
    wrapper.vm.projectTitle = wrapper.vm.project.title;
    await wrapper.vm.$nextTick();

    const routerSpy = jest.spyOn(wrapper.vm.$router, 'push');
    await content.find('#btn-submit').trigger('click');

    expect(wrapper.vm.project.archived).toBe(archive);
    expect(routerSpy).toHaveBeenCalled();
}

function testArchiveComponents(wrapper) {
    const archived = wrapper.vm.project.archived;

    expect(wrapper.find('#dz-archive').exists()).toBe(!archived);
    expect(wrapper.find('#dz-unarchive').exists()).toBe(archived);
    expect(wrapper.findComponent({ref: 'archiveModal'}).exists()).toBe(!archived);
    expect(wrapper.findComponent({ref: 'unarchiveModal'}).exists()).toBe(archived);
}