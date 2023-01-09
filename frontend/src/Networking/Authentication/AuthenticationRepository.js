import {NetworkClient} from "../NetworkClient";
import {HttpMethod} from "../HttpMethod";

export class AuthenticationRepository {

    #networkClient;
    #storedTokenRepository;

    constructor(storedTokenRepository) {
        this.#networkClient = new NetworkClient();
        this.#storedTokenRepository = storedTokenRepository;
    }

    /**
     * Retrieves a user for authentication
     * @param email
     * @param password
     * @return {Promise<>}
     */
    async authenticateWithCredentials(email, password) {
        const path = `/auth/login`;
        const body = {"email": email, "password": password};

        const user = await this.#networkClient.executeRequest(path, HttpMethod.POST, body);

        if (user != null) {
            const token = user.token;
            delete user.token;
            this.#storedTokenRepository.saveToken(token, user);

            return user;
        }
        return null;
    }
}