import { mount } from '@vue/test-utils';
import Expertise from '../../src/components/Scenes/Profile/Expertise.vue';
import ExpertiseModel from '../../src/components/models/Expertise.js';

// Arrange
const dummyExpertises = ExpertiseModel.getDummyExpertiseList();
const dummyUserExpertises = dummyExpertises.filter(expertise => Math.random() < 0.5);
let wrapper;

beforeEach(async function () {

    wrapper = mount(Expertise, {
        propsData: {
            expertises: dummyExpertises,
            userExpertises: dummyUserExpertises
        },
        // mocks: {

        // },
        // stubs: {},
        // methods: {},
        global: {
            provide: {
                "skillsRepository": null
            },
            stubs: ['FontAwesomeIcon', 'Asset']
        }
    });
    wrapper.setData({ user: { "id": 1, "email": "specialist1@test.com", "avatarUrl": "users/avatars/1.avif", "role": "SPECIALIST", "firstName": "Whitney", "lastName": "Keulen" } })
    wrapper.setData({ active: false })
})

// Act
it('Expect transition to exist and inputs to be hidden', function () {
    expect(wrapper.props().expertises).toStrictEqual(dummyExpertises);

    expect(wrapper.findComponent({ name: "transition" }).exists()).toBe(true);

    wrapper.props().expertises.forEach(expertise => {
        expect(wrapper.find(`#input-${expertise.id}`).exists()).toBe(false)
    });

})

it('Transition works, inputs became visible', async function () {
    wrapper.find("#formButton").trigger('click');

    expect(wrapper.vm.active).toBe(true);

    await wrapper.vm.$nextTick();

    const transitionComponent = wrapper.findComponent({ name: "transition" });

    expect(transitionComponent.findAll(".radio-button-container").length).toBe(wrapper.props().expertises.length)

    wrapper.props().expertises.forEach(expertise => {
        expect(wrapper.find(`#input-${expertise.id}`).exists()).toBe(true);
    });

})

it('Correct inputs are active and changing active status work', async function () {
    wrapper.find("#formButton").trigger('click');
    await wrapper.vm.$nextTick();

    wrapper.findAll('input[type="checkbox"]:checked').forEach(async (checkbox) => {
        expect(wrapper.props().userExpertises.map(userExpertise => `input-${userExpertise.id}`).includes(checkbox.attributes().id)).toBe(true);
        await checkbox.setChecked(false)
    });
    await wrapper.vm.$nextTick();
    expect(wrapper.findAll('input[type="checkbox"]:checked').length).toBe(0);

    wrapper.findAll('input[type="checkbox"]').forEach(async (checkbox) => {
        await checkbox.setChecked(true)
        await wrapper.vm.$nextTick();
    });
    expect(wrapper.findAll('input[type="checkbox"]:checked').length).toBe(wrapper.props().expertises.length);
})