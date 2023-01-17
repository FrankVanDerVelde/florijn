import {mount} from '@vue/test-utils'
import PublicProfile from '../../../src/components/Scenes/Profile/PublicProfile.vue'

describe('PublicProfile', () => {
    let wrapper;
    const user = {
        user: {
            role: 'ADMIN',
            firstName: 'John',
            lastName: 'Doe',
            email: 'johndoe@example.com',
            avatarUrl: 'https://example.com/avatar.png'
        }
    }

    beforeEach(() => {
        wrapper = mount(PublicProfile, {
            propsData: {
                user: user,
                skills: [
                    {id: 1, name: 'JavaScript'},
                    {id: 2, name: 'Vue.js'}
                ],
                projects: [
                    {id: 1, name: 'Project A'},
                    {id: 2, name: 'Project B'},
                    {id: 2, name: 'Project C'}
                ],
                availability: []
            },
            methods: {
                viewResume: jest.fn()
            }
        })
    })

    it('should render correct content', () => {
        expect(wrapper.find('p.back-text').text(),
            'Back text is not displaying'
                .toBe('< Terug'));
        expect(wrapper.find('.container.flex.flex-col > div > div > div > div > div').text(),
            'User name is not displaying (the right) information'
                .toBe('John Doe'));
        expect(wrapper.find('.container.flex.flex-col > div > div > div > div > div + div').text(),
            'User role is not displaying (the right) information'
                .toBe('Developer'));
        expect(wrapper.find('.container.flex.flex-col > div > div > div > div > div + div + div').text(),
            'User email is not displaying (the right) information'
                .toBe('johndoe@example.com'));
        expect(wrapper.find('av-error').text(),
            'Validation text of no availability is shown'
                .toBe('Er is geen beschikbaarheid gevonden voor John'));
        expect(wrapper.findAll('.skill-list-summary').length,
            'All skills are shown'.toBe(2));
        expect(wrapper.findAll('.project-list-details-summary').length,
            'All projects are shown'.toBe(3));
    })

    it('should call viewResume method with correct arguments when viewResume button is clicked', () => {
        const viewResume = jest.spyOn(wrapper.vm, 'viewResume');
        wrapper.find('button').trigger('click');
        expect(viewResume).toHaveBeenCalledWith(user);
    });
})
