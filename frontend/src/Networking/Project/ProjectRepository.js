import {NetworkClient} from "../NetworkClient.js";

export class ProjectRepository {

    /**
     * @type {NetworkClient}
     */
    #networkClient;

    constructor() {
        this.#networkClient = new NetworkClient();
    }

    /**
     *
     * @param {null|"ARCHIVED"|"UNARCHIVED"} filter
     * @returns {Promise<Object[]>}
     */
    async fetchProjects(filter = null) {
        return await this.#networkClient.executeRequest(`/projects${filter ? "?filter=" + filter : ""}`);
    }

}