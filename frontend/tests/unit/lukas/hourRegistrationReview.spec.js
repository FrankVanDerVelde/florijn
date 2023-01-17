import ProjectOverview from "../../../src/components/Scenes/Project/Scenes/ProjectOverview.vue";
import {mount} from "@vue/test-utils";
import InMemoryProjectRepo from "../../mockRepos/InMemoryProjectRepo";
import Admin from "../../../src/components/models/user/Admin";
import HoursInfoPopup from "../../../src/components/Scenes/Project/HoursInfoPopup.vue";
import HoursRow from "../../../src/components/Scenes/Project/HoursRow.vue";
import InMemoryHourRegistrationRepo from "../../mockRepos/InMemoryHourRegistrationRepo";
import {HourRegistrationStatus} from "../../../src/components/models/HourRegistration/HourRegistrationStatus";
import {DateService} from "../../../src/Services/DateService";
import Asset from "../../../src/components/Common/Asset.vue";
import Participant from "../../../src/components/Scenes/Project/Participant.vue";
import HourRegistryStatus from "../../../src/components/Scenes/Project/HourRegistryStatus.vue";
import {reactive} from "vue";

let wrapper;

let hourRegistrationRepository;
let projectRepository;

beforeEach(async () => {
    hourRegistrationRepository = new InMemoryHourRegistrationRepo();
    projectRepository = new InMemoryProjectRepo(hourRegistrationRepository);

    wrapper = await mount(ProjectOverview, {
        propsData: {
            project: projectRepository.entities[0],
        },
        computed: {
            user() {
                return new Admin(1, "Bart", "Groene", "", "bart@test.com")
            },
        },
        global: {
            stubs: ['router-link', 'font-awesome-icon', 'Asset', 'SmallTableCard', 'Participant'],
            provide: {
                projectRepository: projectRepository,
                hourRegistrationRepository: hourRegistrationRepository,
                dateService: new DateService(),
            }
        }
    })
});

describe('data validation', () => {
    it('project exists', async () => {
        expect(projectRepository.entities.length).toBeGreaterThan(0);
        expect(wrapper.vm.project).toStrictEqual(projectRepository.entities[0]);
    });
    it('was data loaded', async () => {
        expect(wrapper.vm.reports).toStrictEqual([]);
        expect(wrapper.vm.hourRegistry.length).toBeGreaterThan(0);
    });
});

describe('can click popup', () => {
    it('hour row exists', async () => {
        // wait for Vue created hook to finish before continue testing
        await wrapper.vm.$nextTick();

        expect(wrapper.vm.hourRegistry.length).toBeGreaterThan(0);
        const hour = wrapper.vm.hourRegistry[0];

        expect(hour).toBeDefined();
        let hourRowEl = wrapper.findComponent(HoursRow);

        expect(hourRowEl.exists()).toBe(true);

        // hidden by default.
        expect(hourRowEl.vm.showPopup).toStrictEqual(false);
        expect(wrapper.find(`#hr-popup-${hour.id}`).exists()).toBe(false);

        // clicking on the row
        await hourRowEl.find('tr').trigger('click');
        await wrapper.vm.$nextTick();
        expect(hourRowEl.vm.showPopup).toStrictEqual(true);
        expect(wrapper.find(`#hr-popup-${hour.id}`).exists()).toBe(true);
    });
    it('popup contains right data', async() => {
        const hour = wrapper.vm.hourRegistry[0];
        expect(hour).toBeDefined();
        expect(hour.status).toStrictEqual(null);

        let hourRowEl = wrapper.findComponent(HoursRow);
        expect(hourRowEl.html()).toContain("In afwachting");

        await hourRowEl.find('tr').trigger('click');
        await wrapper.vm.$nextTick();

        let popupEl = wrapper.findComponent(HoursInfoPopup);

        expect(popupEl.find('#review-btns').exists()).toBe(true);
        expect(popupEl.find('#review-btns .reject').exists()).toBe(true);
        expect(popupEl.find('#review-btns .accept').exists()).toBe(true);
    });
    it('can reject and accept', async () => {
        const rowWrapper = await mount(HoursRow, {
            props: {
                registry: reactive(hourRegistrationRepository.entities[0]),
            },
            data() {
                return {
                    showPopup: true,
                }
            },
            global: {
                stubs: ['router-link', 'font-awesome-icon', 'Asset', 'SmallTableCard', 'Participant'],
                provide: {
                    hourRegistrationRepository: hourRegistrationRepository,
                    dateService: new DateService(),
                }
            }
        });

        rowWrapper.vm.showPopup = true;
        await rowWrapper.vm.$nextTick();

        const popupWrapper = rowWrapper.findComponent(HoursInfoPopup);

        expect(rowWrapper.vm.registry).toBeDefined();
        expect(rowWrapper.vm.registry.status).toStrictEqual(null);

        let acceptButton = popupWrapper.find('.accept');
        await acceptButton.trigger('click');
        await rowWrapper.vm.$nextTick();

        expect(rowWrapper.vm.registry.status).toStrictEqual(HourRegistrationStatus.accepted);

        rowWrapper.vm.registry.status = null;
        await rowWrapper.vm.$nextTick();
        expect(rowWrapper.vm.registry.status).toStrictEqual(null);

        await popupWrapper.find('.reject').trigger('click');
        await rowWrapper.vm.$nextTick();
        expect(rowWrapper.vm.registry.status).toStrictEqual(HourRegistrationStatus.rejected);
    });

});
