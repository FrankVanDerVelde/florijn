import { mount } from "@vue/test-utils";
import PersonalInfo from '../../src/components/Scenes/Profile/Profile.vue';
import InMemoryEntitiesService from '../mockRepos/InMemoryEntitiesService.js';
import Participant from '../../src/components/models/Participant.js';
let inMemoryUserRepo;

test("has data", () => {
    expect(typeof PersonalInfo.data).toBe("function");
});

test('Profile renders and snapshot is still correct', async () => {
    inMemoryUserRepo = new InMemoryEntitiesService(10000, Participant.createDummy);

    const mockRouter = {
        push: jest.fn()
    }

    const wrapper = mount(PersonalInfo, {
        global: {
            mocks: {
                $router: mockRouter
            },
            provide: {
                "userRepository": inMemoryUserRepo
            },
            stubs: ['FontAwesomeIcon', 'Asset', 'router-link', 'router-view']
        }
    })

    expect(wrapper.isVisible).toBeTruthy();

    expect(mockRouter.push).toHaveBeenCalledTimes(1)

    expect(wrapper.element).toMatchSnapshot()
})