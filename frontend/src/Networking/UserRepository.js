import {NetworkClient} from "./NetworkClient.js";
import {HttpMethod} from "./HttpMethod.js";

export class UserRepository {

    #networkClient

    constructor() {
        this.#networkClient = new NetworkClient();
    }

    /**
     * Adds a client.
     * @param {String} name Name of the Client.
     * @param {String} email Chosen email of the Client.
     * @param {String} password Chosen password of the Client (plain-text)
     * @param {String|null} avatarUrl URL of the avatar of the Client.
     * @param {String|null} bannerSrc Default banner to use.
     * @return {Promise<Client|Object|null>} Added Client.
     */
    async addClient(name, email, password, avatarUrl, bannerSrc) {
        const path = `/users/add/client`;
        const body = {
            name: name,
            email: email,
            password: password,
            avatarUrl: avatarUrl,
            bannerSrc: bannerSrc
        };
        return await this.#networkClient.executeRequest(path, HttpMethod.POST, body);
    }
}
