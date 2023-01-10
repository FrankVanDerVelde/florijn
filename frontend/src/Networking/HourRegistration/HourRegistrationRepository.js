import {HourRegistration} from "../../components/models/HourRegistration/HourRegistration.js";
import FetchService from "../../Services/FetchService.js";
import {NetworkClient} from "../NetworkClient.js";
import {HttpMethod} from "../HttpMethod.js";

/**
 * Endpoint for retrieving hour registrations for a specialist
 * @author Martijn van der Wal
 */
export class HourRegistrationRepository {
    #networkClient;

    constructor() {
        this.#networkClient = new NetworkClient();
    }

    /**
     * Fetches all HourRegistrations for specified specialist.
     * @param {Number} specialistId specialist id
     * @return {Promise<[HourRegistration|Object]>} List of HourRegistration objects.
     */
    async fetchAllFor(specialistId) {
        const path = `/users/${specialistId}/hour-registrations/`;
        let results = await this.#networkClient.executeRequest(path);
        return results.map(HourRegistration.fromJSON)
    }

    /**
     * Deletes HourRegistration for specified id.
     * @param {Number} id id of the HourRegistration to delete.
     * @return {Promise<HourRegistration|Object>} deleted HourRegistration.
     */
    async deleteHourRegistration(id) {
        const path = `/hour-registrations/${id}/delete/`;
        let result = await this.#networkClient.executeRequest(path, HttpMethod.DELETE);
        return HourRegistration.fromJSON(result);
    }

    /**
     * Fetches a single HourRegistration.
     * @param {Number} hourRegistrationId Id of the hour registration.
     * @return {Promise<HourRegistration|Object>} HourRegistration object.
     */
    async fetch(hourRegistrationId) {
        const path = `/hour-registrations/${hourRegistrationId}`;
        let result = await this.#networkClient.executeRequest(path);
        return HourRegistration.fromJSON(result);
    }

    /**
     * Updates the HourRegistration
     * @param {Number} hourRegistrationId id of the HourRegistration
     * @param {Number} projectId id of the Project
     * @param {Number} userId Id of the User
     * @param {String} from Timestamp the HourRegistration should start, format: YYYY-MM-dd HH:mm
     * @param {String} to Timestamp the HourRegistration should end, format: YYYY-MM-dd HH:mm
     * @param {String} description Description of the HourRegistration activity.
     * @return {Promise<HourRegistration|Object>} Updated HourRegistration object.
     */
    async update(hourRegistrationId, projectId, userId, from, to, description) {
        const path = `/hour-registrations/${hourRegistrationId}/update`;
        const body = {
            project_id: projectId,
            user_id: userId,
            from: from,
            to: to,
            description: description
        }
        let result = await this.#networkClient.executeRequest(path, HttpMethod.PUT, body);
        return HourRegistration.fromJSON(result);
    }

    /**
     * Creates a new HourRegistration.
     * @param {Number} projectId id of the Project
     * @param {Number} userId Id of the User
     * @param {String} from Timestamp the HourRegistration should start, format: YYYY-MM-dd HH:mm
     * @param {String} to Timestamp the HourRegistration should end, format: YYYY-MM-dd HH:mm
     * @param {String} description Description of the HourRegistration activity.
     * @return {Promise<HourRegistration|Object>} Created HourRegistration object.
     */
    async create(projectId, userId, from, to, description) {
        const path = `/users/${userId}/hour-registrations/`;
        let body = {
            project_id: projectId,
            from: from,
            to: to,
            description: description
        };
        let result = await this.#networkClient.executeRequest(path, HttpMethod.POST, body);
        return HourRegistration.fromJSON(result);
    }

    async acceptHourRegistration(hourRegistrationId) {
        return await this.#networkClient.executeRequest(`/hour-registrations/${hourRegistrationId}/accept`, HttpMethod.POST);
    }

    async rejectHourRegistration(hourRegistrationId) {
        return await this.#networkClient.executeRequest(`/hour-registrations/${hourRegistrationId}/reject`, HttpMethod.POST);
    }


}