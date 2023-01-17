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
        // making sure the project was loaded into the repository and into the component.
        expect(projectRepository.entities.length).toBeGreaterThan(0);
        expect(wrapper.vm.project).toStrictEqual(projectRepository.entities[0]);
    });
    it('was data loaded', async () => {
        // making sure some other data was loaded into the component correctly.
        expect(wrapper.vm.reports).toStrictEqual([]);
        expect(wrapper.vm.hourRegistry.length).toBeGreaterThan(0);
    });
});

describe('can click popup', () => {
    it('hour row exists', async () => {
        // wait for Vue created hook to finish before continue testing
        await wrapper.vm.$nextTick();

        // checking if there is any hour registry in the list of the component
        expect(wrapper.vm.hourRegistry.length).toBeGreaterThan(0);
        const hour = wrapper.vm.hourRegistry[0];

        // checking if the hour registry row component exists on the page.
        expect(hour).toBeDefined();
        let hourRowEl = wrapper.findComponent(HoursRow);
        expect(hourRowEl.exists()).toBe(true);

        // check if the review popup is not open (closed by default).
        expect(hourRowEl.vm.showPopup).toStrictEqual(false);
        expect(wrapper.find(`#hr-popup-${hour.id}`).exists()).toBe(false);

        // clicking on the row, opening the popup.
        await hourRowEl.find('tr').trigger('click');
        await wrapper.vm.$nextTick();

        // making sure the popup has opened.
        expect(hourRowEl.vm.showPopup).toStrictEqual(true);
        expect(wrapper.find(`#hr-popup-${hour.id}`).exists()).toBe(true);
    });
    it('popup contains right data', async() => {
        // making sure the data was loaded in correctly.
        const hour = wrapper.vm.hourRegistry[0];
        expect(hour).toBeDefined();
        expect(hour.status).toStrictEqual(null);

        // making sure that the status is null and that it is yet to be reviewed.
        let hourRowEl = wrapper.findComponent(HoursRow);
        expect(hourRowEl.html()).toContain("In afwachting");

        // clicking on the row, opening the popup.
        await hourRowEl.find('tr').trigger('click');
        await wrapper.vm.$nextTick();
        let popupEl = wrapper.findComponent(HoursInfoPopup);

        // checking if the buttons are displayed correctly.
        expect(popupEl.find('#review-btns').exists()).toBe(true);
        expect(popupEl.find('#review-btns .reject').exists()).toBe(true);
        expect(popupEl.find('#review-btns .accept').exists()).toBe(true);
    });
    it('can reject and accept', async () => {
        // loading the hour registry row component.
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

        // making sure the popup is open.
        rowWrapper.vm.showPopup = true;
        await rowWrapper.vm.$nextTick();
        const popupWrapper = rowWrapper.findComponent(HoursInfoPopup);

        // making sure the registry status is still null (not reviewed yet).
        expect(rowWrapper.vm.registry).toBeDefined();
        expect(rowWrapper.vm.registry.status).toStrictEqual(null);

        // click the accept button and making sure the status is accepted after.
        let acceptButton = popupWrapper.find('.accept');
        await acceptButton.trigger('click');
        await rowWrapper.vm.$nextTick();

        expect(rowWrapper.vm.registry.status).toStrictEqual(HourRegistrationStatus.accepted);

        // resetting the status to null and checking if it updated, so we can review again.
        rowWrapper.vm.registry.status = null;
        await rowWrapper.vm.$nextTick();
        expect(rowWrapper.vm.registry.status).toStrictEqual(null);

        // click the reject button and making sure the status is rejected after.
        await popupWrapper.find('.reject').trigger('click');
        await rowWrapper.vm.$nextTick();
        expect(rowWrapper.vm.registry.status).toStrictEqual(HourRegistrationStatus.rejected);
    });

});
