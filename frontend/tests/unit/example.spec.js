import { mount, shallowMount } from '@vue/test-utils'
import PersonalInfo from '../../src/components/Scenes/Profile/PersonalInfo.vue';
import MockUserRepo from '../mockRepos/MockUserRepository.js';

let wrapper;

beforeEach(async function () {
  let userRepository = userRepository;

  wrapper = shallowMount(PersonalInfo, {
    propsData: {},
    mocks: {
      user: { "id": 7, "email": "specialist1@test.com", "avatarUrl": "users/avatars/1.avif", "role": "SPECIALIST", "firstName": "Whitney", "lastName": "Keulen" }
    },
    // stubs: {},
    // methods: {},
    global: {
      provide: {
        "userRepository": MockUserRepo
      },
      stubs: ['FontAwesomeIcon']
    }
  });


})

it('renders properly', () => {

  // expect(wrapper.text()).toMatch(msg)
})

