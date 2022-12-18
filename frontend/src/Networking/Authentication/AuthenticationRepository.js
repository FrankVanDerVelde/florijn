import {NetworkClient} from "../NetworkClient";
import {HttpMethod} from "../HttpMethod";
import {StoredTokenRepository} from "./StoredTokenRepository";
import CONFIG from '/config.js'

export class AuthenticationRepository {

    #networkClient;
    #storedTokenRepository;

    constructor() {
        this.#networkClient = new NetworkClient();
        this.#storedTokenRepository = new StoredTokenRepository(CONFIG.JWT_STORAGE_ITEM);
    }

    /**
     * Fetches a list of Availability for the specified week
     * @param email
     * @param password
     * @return {Promise<>}
     */
    async authenticateWithCredentials(email, password) {
        const path = `/auth/login`;
        const body = {"email": email, "password": password};

        const response = await this.#networkClient.executeRequest(path, HttpMethod.POST, body);
        console.log("response= "+ response)

        if (response.ok) {
            let user = await response.json();
            this.#storedTokenRepository.saveToken(
                response.headers.get('Authorization'),
                user);
            return user;
        } else {
            console.log(response)
            return null;
        }
    }
}