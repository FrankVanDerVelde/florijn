import { mount, shallowMount } from '@vue/test-utils'
import PersonalInfo from '../../src/components/Scenes/Profile/PersonalInfo.vue';
import InMemoryEntitiesService from '../mockRepos/InMemoryEntitiesService.js';
import Participant from '../../src/components/models/Participant.js';

let wrapper;
let inMemoryUserRepo;

beforeEach(function () {

  inMemoryUserRepo = new InMemoryEntitiesService(10000, Participant.createDummy);

  wrapper = shallowMount(PersonalInfo, {
    propsData: {},
    mocks: {
      user: { "id": 1, "email": "specialist1@test.com", "avatarUrl": "users/avatars/1.avif", "role": "SPECIALIST", "firstName": "Whitney", "lastName": "Keulen" }
    },
    // stubs: {},
    // methods: {},
    global: {
      provide: {
        "userRepository": inMemoryUserRepo
      },
      stubs: ['FontAwesomeIcon']
    }
  });

})

test('Proper initalization of the user service', function () {
  expect(inMemoryUserRepo.entities.length).toBeGreaterThan(0);
})

test('findAll returns all', function () {
  let users = inMemoryUserRepo.findAll();
  expect(users)
    .toStrictEqual(inMemoryUserRepo.entities);
})

test('Find by id returns the specified user', function () {
  const user1 = inMemoryUserRepo.entities[0];
  const user2 = inMemoryUserRepo.entities[Math.trunc(inMemoryUserRepo.entities.length / 2)];
  const user3 = inMemoryUserRepo.entities[inMemoryUserRepo.entities.length - 1];

  expect(inMemoryUserRepo.findById(user1.id))
    .toStrictEqual(user1);
  expect(inMemoryUserRepo.findById(user2.id))
    .toStrictEqual(user2);
  expect(inMemoryUserRepo.findById(user3.id))
    .toStrictEqual(user3);
  expect(inMemoryUserRepo.findById(99999))
    .toBeUndefined();
})

test('deleteById deletes the specified user', function () {
  const user1 = inMemoryUserRepo.entities[0];

  inMemoryUserRepo.deleteById(user1.id);

  expect(inMemoryUserRepo.findById(user1.id))
    .toBeUndefined();
})

