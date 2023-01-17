import {AuthenticationRepository} from '../../../src/Networking/Authentication/AuthenticationRepository';
import {StoredTokenRepository} from '../../../src/Networking/Authentication/StoredTokenRepository';

describe('AuthenticationRepository', () => {
    let authenticationRepository;
    let storedTokenRepository;

    beforeEach(() => {
        storedTokenRepository = new StoredTokenRepository('test_token');
        authenticationRepository = new AuthenticationRepository(storedTokenRepository);
    });

    it('authenticateWithCredentials returns a user and saves the token', async () => {
        // Mock the executeRequest method to return a user object with a token
        authenticationRepository.#networkClient.executeRequest = jest.fn(() => {
            return {
                token: 'test_token',
                user: {
                    id: 1,
                    email: "admin1@test.com",
                    avatarUrl: "users/avatars/admin1.jpeg",
                    role: "ADMIN",
                    firstName: "Maria",
                    lastName: "de Jong"
                }
            };
        });

        const user = await authenticationRepository.authenticateWithCredentials('admin1@test.com', 'test');

        expect(user,
            'The user found is the user stored'
                .toEqual({firstName: 'Maria', lastName: 'de Jong'}));
        expect(storedTokenRepository.#token,
            'The token stored is the token got from the login'
                .toEqual('test_token'));
        expect(storedTokenRepository.#user,
            'The user stored is the token got from the login'
                .toEqual({firstName: 'Maria', lastName: 'de Jong'}));
    });
});