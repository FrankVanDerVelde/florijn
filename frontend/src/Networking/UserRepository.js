import {NetworkClient} from "./NetworkClient.js";
import {HttpMethod} from "./HttpMethod.js";

export class UserRepository {

    #networkClient

    constructor() {
        this.#networkClient = new NetworkClient();
    }

    async getAllUsers() {
        return await this.#networkClient.executeRequest(`/users`);
    }

    async getUserCounts() {
        return await this.#networkClient.executeRequest(`/users/counts`);
    }

    async getUserById(userId) {
        return await this.#networkClient.executeRequest(`/users/${userId}`);
    }

    async updateUser(userId, body) {
        return await this.#networkClient.executeRequest(`/users/${userId}/edit`, HttpMethod.PUT, body);
    }

    async getResumeById(userId) {
        return await this.#networkClient.executeRequest(`/users/${userId}/resume`);
    }

    async updateResume(userId, body) {
        return await this.#networkClient.executeRequest(`/users/${userId}/resume`, HttpMethod.PUT, body);
    }

    async fetchUserCounts() {
        return await this.#networkClient.executeRequest(`/users/counts`);
    }

    async deleteUserById(userId) {
        return await this.#networkClient.executeRequest(`/users/${userId}`, HttpMethod.DELETE);
    }

    async getUsersAdressById(userId) {
        return await this.#networkClient.executeRequest(`/users/adress/${userId}`);
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
    async addClient(name, email, password, avatarUrl = null, bannerSrc = null) {
        const path = `/users/add/client`;
        const body = {
            email: email,
            name: name,
            password: password,
            avatarUrl: avatarUrl,
            bannerSrc: bannerSrc,
        };

        let formData = this.#createFormDataFromBody(body);

        return await this.#networkClient.executeRequestWithFormData(path, HttpMethod.POST, formData);
    }

    /**
     * Adds a specialist.
     * @param {String} firstname Name of the Client.
     * @param {String} lastname
     * @param {String} email Chosen email of the Client.
     * @param {String} password Chosen password of the Client (plain-text)
     * @param {String|null} avatarUrl URL of the avatar of the Client.
     * @return {Promise<Client|Object|null>} Added Specialist.
     */
    async addSpecialist(firstname, lastname, email, password, avatarUrl = null) {
        const path = `/users/add/specialist`;

        const body = {
            firstname: firstname,
            lastname: lastname,
            email: email,
            password: password,
            avatarUrl: avatarUrl,
        };

        let formData = this.#createFormDataFromBody(body);

        return await this.#networkClient.executeRequestWithFormData(path, HttpMethod.POST, formData);
    }

    #createFormDataFromBody(body) {
        let formData = new FormData();
        for (const [key, value] of Object.entries(body)) {
            formData.append(key, value);
        }
        return formData;
    }

    /**
     * Fetches users, optionally filtered with a specified role.
     * @param {UserRole|null} role Fetches all users with a specified role, defaults to null.
     * @return {Promise<[User|Object]>} Fetched users
     */
    async fetchUsers(role = null) {
        const path = role ? `/users/role/${role}` : `/users/`;
        return await this.#networkClient.executeRequest(path);
    }

    // get user by id
    async getUserById(id) {
        const path = `/users/${id}`;
        return await this.#networkClient.executeRequest(path);
    }

    // get users address
    async getUserAddress(id) {
        const path = `/users/address/${id}`;
        return await this.#networkClient.executeRequest(path);
    }

    async getUserAddress(id) {
        const path = `/users/address/${id}`;
        return await this.#networkClient.executeRequest(path);
    }

    async updateUser(userId, formData) {
        return await this.#networkClient.executeRequestWithFormData(`/users/${userId}/edit`, HttpMethod.PUT, formData);
    }

    async getResume(userId) {
        const path = `/users/${userId}/resume`;
        return await this.#networkClient.executeRequest(path);
    }

    async updateResume(userId, formData) {
        return await this.#networkClient.executeRequestWithFormData(`/users/${userId}/resume`, HttpMethod.PUT, formData);
    }

    // async updateProject(projectId, formData) {
    //     return await this.#networkClient.executeRequestWithFormData(`/projects/${projectId}/update`, HttpMethod.PUT, formData);
    // }

    async fetchUsersBySkills(body) {
        const path = `/users/specialists/skills`;
        return await this.#networkClient.executeRequest(path, HttpMethod.POST, body);
    }
}
