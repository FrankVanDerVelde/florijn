import {mount} from '@vue/test-utils'
const LogIn = require('../../../src/components/Scenes/Authentication/LogIn');

describe('LogIn.vue', () => {
    let wrapper;
    const VALIDATION_TEXT = 'De inloggegevens zijn onjuist ingevuld! Probeer het nogmaals';

    beforeEach(() => {
        wrapper = mount(LogIn);
    })

    it('should render correct input fields', () => {
        const inputEmail = wrapper.find('input[type="email"]');
        const inputPassword = wrapper.find('input[type="password"]');
        expect(inputEmail.exists(),
            'Email input field is not rendered')
            .toBe(true);
        expect(inputPassword.exists(),
            'Password input field is not rendered'
                .toBe(true));
    })

    it('should display error message when login credentials are incorrect', async () => {
        wrapper.find('form').trigger('submit');
        await wrapper.vm.$nextTick();
        expect(wrapper.find('.validationText').text(),
            'Validation message is not displayed by leaving input field blank'
                .toBe(VALIDATION_TEXT));

        wrapper.find('input[type="email"]').setValue('test');
        wrapper.find('input[type="password"]').setValue('test');
        wrapper.find('form').trigger('submit');
        await wrapper.vm.$nextTick();
        expect(wrapper.find('.validationText').text(),
            'Validation message is not displayed by putting incorrect credentials'
                .toBe(VALIDATION_TEXT));
    })

    it('should navigate to home page when login is successful', async () => {
        wrapper.find('input[type="email"]').setValue('admin1@test.com');
        wrapper.find('input[type="password"]').setValue('test');
        wrapper.find('form').trigger('submit');
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.$route.name,
            ("Expected route name to be 'home' but got " + wrapper.vm.$route.name)
                .toBe('home'));
    })
})