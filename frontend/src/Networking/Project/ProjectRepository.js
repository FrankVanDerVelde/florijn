import {NetworkClient} from "../NetworkClient.js";
import {HttpMethod} from "../HttpMethod.js";

export class ProjectRepository {

    /**
     * @type {NetworkClient}
     */
    #networkClient;

    constructor() {
        this.#networkClient = new NetworkClient();
    }

    /**
     * @param {undefined|"ARCHIVED"|"UNARCHIVED"} filter
     * @param {undefined|string}query
     * @returns {Promise<Object[]>}
     */
    async fetchProjects(filter = undefined, query = undefined) {
        let urlSearchParams = new URLSearchParams();
        if (filter) urlSearchParams.set("filter", filter);
        if (query) urlSearchParams.set("query", query);

        let s = urlSearchParams.toString();
        return await this.#networkClient.executeRequest(`/projects${s.length === 0 ? "" : "?" + s}`);
    }

    async fetchProjectById(projectId) {
        return await this.#networkClient.executeRequest(`/projects/${projectId}`);
    }

    async fetchProjectReports(projectId) {
        return await this.#networkClient.executeRequest(`/projects/${projectId}/reports`);
    }

    async fetchProjectHourRegistrationsForUser(projectId, userId) {
        return await this.#networkClient.executeRequest(`/projects/${projectId}/hour-registrations`);
    }

    async fetchTotalProjects() {
        return await this.#networkClient.executeRequest(`/projects/total`);
    }

    async fetchTotalWorkedHours() {
        return await this.#networkClient.executeRequest(`/projects/hours`);
    }

    async fetchEarnings() {
        return await this.#networkClient.executeRequest(`/projects/earnings`);
    }

    async archiveProject(projectId, body) {
        return await this.#networkClient.executeRequest(`/projects/${projectId}/archive`, HttpMethod.POST, body);
    }

    async unarchiveProject(projectId, body) {
        return await this.#networkClient.executeRequest(`/projects/${projectId}/unarchive`, HttpMethod.POST, body);
    }

    async transferProject(projectId, body) {
        return await this.#networkClient.executeRequest(`/projects/${projectId}/transfer-ownership`, HttpMethod.POST, body);
    }

    async createProject(formData) {
        return await this.#networkClient.executeRequestWithFormData(`/projects/create`, HttpMethod.POST, formData);
    }

    async updateProject(projectId, formData) {
        return await this.#networkClient.executeRequestWithFormData(`/projects/${projectId}/update`, HttpMethod.PUT, formData);
    }

}