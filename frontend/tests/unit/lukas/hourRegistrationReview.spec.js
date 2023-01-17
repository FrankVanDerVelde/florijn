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
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

let wrapper;
const hourRegistrationRepository = new InMemoryHourRegistrationRepo();
const projectRepository = new InMemoryProjectRepo(hourRegistrationRepository);

beforeEach(() => {
    wrapper = mount(ProjectOverview, {
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

        setTimeout(() => {

            expect(wrapper.vm.hourRegistry.length).toBeGreaterThan(0);
            const hour = wrapper.vm.hourRegistry[0];

            expect(hour).toBeDefined();
            let hourRowEl = wrapper.findComponent(HoursRow);

            expect(hourRowEl.exists()).toBe(true);

            // hidden by default.
            expect(hourRowEl.vm.showPopup).toStrictEqual(false);
            expect(wrapper.find(`#hr-${hour.id} #hr-popup`).exists()).toBe(false);

            // clicking on the row
            hourRowEl.trigger('click');
            expect(hourRowEl.vm.showPopup).toStrictEqual(true);
            expect(wrapper.find(`#hr-${hour.id} #hr-popup`).exists()).toBe(true);
        }, 10)
    });
    it('popup contains right data', () => {
        const hour = wrapper.vm.hourRegistry[0];
        expect(hour).toBeDefined();
        expect(hour.status).toStrictEqual(null);

        setTimeout(() => {
            let hourRowEl = wrapper.findComponent(HoursRow);
            expect(hourRowEl.html()).toContain("In afwachting");

            hourRowEl.trigger('click');

            let popupEl = wrapper.findComponent(HoursInfoPopup);

            expect(popupEl.find('#review-btns').exists()).toBe(true);
            expect(popupEl.find('#review-btns .reject').exists()).toBe(true);
            expect(popupEl.find('#review-btns .accept').exists()).toBe(true);
        }, 10);
    });
    it('can reject and accept', () => {
        const hour = wrapper.vm.hourRegistry[0];

        const rowWrapper = mount(HoursRow, {
            propsData: {
                registry: hour,
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

        console.log(rowWrapper.html());
        const popupWrapper = rowWrapper.findComponent(HoursInfoPopup);

        expect(hour).toBeDefined();
        expect(hour.status).toStrictEqual(null);

        popupWrapper.trigger('changeStatus', true);
        expect(hour.status).toStrictEqual(HourRegistrationStatus.accepted);
        expect(rowWrapper.html()).toContain("Goedgekeurd");

        hour.status = null;
        expect(hour.status).toStrictEqual(null);
        expect(rowWrapper.html()).toContain("In afwachting");

        popupWrapper.trigger('changeStatus', false);
        expect(hour.status).toStrictEqual(HourRegistrationStatus.rejected);
        expect(rowWrapper.html()).toContain("Afgewezen");
        expect("smash").toStrictEqual("lol");
    });

});
