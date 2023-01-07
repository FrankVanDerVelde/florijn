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
     * @param {undefined|Number|string} userId
     * @param {undefined|"ARCHIVED"|"UNARCHIVED"} filter
     * @param {undefined|string}query
     * @returns {Promise<Object[]>}
     */
    async fetchProjects(userId = undefined, filter = undefined, query = undefined) {
        let urlSearchParams = new URLSearchParams();
        if (userId) urlSearchParams.set("userId", userId);
        if (filter) urlSearchParams.set("filter", filter);
        if (query) urlSearchParams.set("query", query);

        let s = urlSearchParams.toString();
        return await this.#networkClient.executeRequest(`/projects${s.length === 0 ? "" : "?" + s}`);
    }

    async fetchTotalProjects(userId = null) {
        return await this.#networkClient.executeRequest(`/projects/total${userId ? "?userId=" + userId : ""}`);
    }

    async fetchTotalWorkedHours(userId) {
        return await this.#networkClient.executeRequest(`/projects/hours?userId=${userId}`);
    }

    async fetchEarnings(userId) {
        return await this.#networkClient.executeRequest(`/projects/earnings?userId=${userId}`);
    }

}