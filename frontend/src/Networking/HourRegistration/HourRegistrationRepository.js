import {HourRegistration} from "../../components/models/HourRegistration.js";
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
        return await this.#networkClient.executeRequest(path);
    }

    /**
     * Deletes HourRegistration for specified id.
     * @param {Number} id id of the HourRegistration to delete.
     * @return {Promise<HourRegistration|Object>} deleted HourRegistration.
     */
    async deleteHourRegistration(id) {
        const path = `/hour-registrations/${id}/delete/`;
        return await this.#networkClient.executeRequest(path, HttpMethod.DELETE);
    }

    /**
     * Fetches a single HourRegistration.
     * @param {Number} hourRegistrationId Id of the hour registration.
     * @return {Promise<HourRegistration|Object>} HourRegistration object.
     */
    async fetch(hourRegistrationId) {
        const path = `/hour-registrations/${hourRegistrationId}`;
        return await this.#networkClient.executeRequest(path);
    }

    /**
     * Updates the HourRegistration
     * @param hourRegistrationId id of the HourRegistration
     * @param projectId id of the Project
     * @param userId Id of the User
     * @param from Timestamp the HourRegistration should start, format: YYYY-MM-dd HH:mm
     * @param to Timestamp the HourRegistration should end, format: YYYY-MM-dd HH:mm
     * @param description Description of the HourRegistration activity.
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
        return await this.#networkClient.executeRequest(path, HttpMethod.PUT, body);
    }

    /**
     * Creates a new HourRegistration.
     * @param projectId id of the Project
     * @param userId Id of the User
     * @param from Timestamp the HourRegistration should start, format: YYYY-MM-dd HH:mm
     * @param to Timestamp the HourRegistration should end, format: YYYY-MM-dd HH:mm
     * @param description Description of the HourRegistration activity.
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
        return await this.#networkClient.executeRequest(path, HttpMethod.POST, body);
    }
}