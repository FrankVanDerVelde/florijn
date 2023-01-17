import Home from "../../../src/components/Scenes/Home/BaseHome";
import LogIn from "../../../src/components/Scenes/Authentication/LogIn.vue";
import InMemoryAuthenticationRepo from "../../mockRepos/InMemoryAuthenticationRepository.js";
import {createMemoryHistory, createRouter} from "vue-router";
import {mount} from '@vue/test-utils'

const authenticationRepository = new InMemoryAuthenticationRepo();
let wrapper;
const VALIDATION_TEXT = 'De inloggegevens zijn onjuist ingevuld! Probeer het nogmaals';


describe('LogIn.vue', () => {

    const router = createRouter({
        history: createMemoryHistory(),
        routes: [{path: '/login', component: LogIn}, {path: '/home', component: {default: Home}}]
    })

    beforeEach(() => {
        wrapper = mount(LogIn, {
            global: {
                provide: {
                    "authenticationRepository": authenticationRepository
                },
                plugins: [router],
            }

        });
    })

    it('should render correct input fields', () => {
        const inputEmail = wrapper.find('input[type="email"]');
        const inputPassword = wrapper.find('input[type="password"]');
        expect(inputEmail.exists()).toBeTruthy();
        expect(inputPassword.exists()).toBeTruthy();
    });

    it('should display error message when login credentials are incorrect', async () => {
        wrapper.find('form').trigger('submit');
        await wrapper.vm.$nextTick();
        await wrapper.vm.$nextTick();
        expect(wrapper.find('.error-text').text()).toBe(VALIDATION_TEXT);

        wrapper.find('input[type="email"]').setValue('test');
        wrapper.find('input[type="password"]').setValue('test');
        wrapper.find('form').trigger('submit');
        await wrapper.vm.$nextTick();
        await wrapper.vm.$nextTick();
        expect(wrapper.find('.error-text').text()).toBe(VALIDATION_TEXT);
    })

    it('should navigate to home page when login is successful', async () => {
        const onRouterPushSpy = jest.spyOn(wrapper.vm.$router, 'push');

        wrapper.find('input[type="email"]').setValue('admin1@test.com');
        wrapper.find('input[type="password"]').setValue('test');
        wrapper.find('form').trigger('submit');
        await wrapper.vm.$nextTick();
        expect(onRouterPushSpy).toHaveBeenCalledWith({"name": "home"});
    })
})