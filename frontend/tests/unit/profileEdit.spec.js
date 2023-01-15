import { mount, shallowMount } from '@vue/test-utils'
import PersonalInfo from '../../src/components/Scenes/Profile/PersonalInfo.vue';
import InMemoryEntitiesService from '../mockRepos/InMemoryEntitiesService.js';
import Participant from '../../src/components/models/Participant.js';

let wrapper;
let inMemoryUserRepo;

beforeEach(function () {

  inMemoryUserRepo = new InMemoryEntitiesService(10000, Participant.createDummy);

  wrapper = mount(PersonalInfo, {
    propsData: {},
    // mocks: {
      
    // },
    // stubs: {},
    // methods: {},
    global: {
      provide: {
        "userRepository": inMemoryUserRepo
      },
      stubs: ['FontAwesomeIcon', 'Asset']
    }
  });
  wrapper.setData({user: { "id": 1, "email": "specialist1@test.com", "avatarUrl": "users/avatars/1.avif", "role": "SPECIALIST", "firstName": "Whitney", "lastName": "Keulen" }})

  wrapper.setData({address: {place: "Hoorn", street: "Noorderplantsoen", houseNumber: "3", houseNumberAddition: "B", postalCode: "1643AK"}})


  // avatarFile: null,
  // bannerFile: null,
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

test('form fields load data succesfully', function () {
  // console.log(wrapper)
  expect(wrapper.find("#voornaam").element.value).toBe(wrapper.vm.user.firstName);
  expect(wrapper.find("#achternaam").element.value).toBe(wrapper.vm.user.lastName);
  expect(wrapper.find("#email").element.value).toBe(wrapper.vm.user.email);

  expect(wrapper.find("#woonplaats").element.value).toBe(wrapper.vm.address.place);
  expect(wrapper.find("#straat").element.value).toBe(wrapper.vm.address.street);
  expect(wrapper.find("#huisnummer").element.value).toBe(wrapper.vm.address.houseNumber);
  expect(wrapper.find("#toevoeging").element.value).toBe(wrapper.vm.address.houseNumberAddition);
  expect(wrapper.find("#postcode").element.value).toBe(wrapper.vm.address.postalCode);

})