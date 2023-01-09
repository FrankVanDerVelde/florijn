import {HourRegistration} from "../../components/models/HourRegistration.js";
import {NetworkClient} from "../NetworkClient.js";
import {HttpMethod} from "../HttpMethod.js";

/**
 * Endpoint for retrieving hour registrations for a specialist
 * @author Martijn van der Wal
 */
export class HourRegistrationRepository {
    fetcher;

    constructor() {
        this.fetcher = new NetworkClient();
    }

    /** Methods **/
    async fetchAllFor(specialistId) {
        try {
            let jsonHrs = await this.fetcher.executeRequest(`/users/${specialistId}/hour-registrations/`);
            return jsonHrs.map(HourRegistration.fromJSON)
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    async deleteHourRegistration(id) {
        try {
            return await this.fetcher.executeRequest(`/hour-registrations/${id}/delete/`, HttpMethod.DELETE);
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    async fetch(hourRegistrationId) {
        try {
            return this.fetcher.executeRequest(`/hour-registrations/${hourRegistrationId}`);
        } catch (e) {
            console.error(e);
        }
    }

    async update(hourRegistrationId, projectId, userId, from, to, description) {
        try {
            return await this.fetcher.executeRequest(
                `/hour-registrations/${hourRegistrationId}/update`,
                HttpMethod.PUT,
                {
                    project_id: projectId,
                    user_id: userId,
                    from: from,
                    to: to,
                    description: description
                },
                {
                    'Content-Type': 'application/json'
                },
            );
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    async create(projectId, userId, from, to, description) {
        let body = {
            project_id: projectId,
            from: from,
            to: to,
            description: description
        };

        return await this.fetcher.executeRequest(`/users/${userId}/hour-registrations/`, HttpMethod.POST, body);
    }

    async acceptHourRegistration(hourRegistrationId) {
        return await this.fetcher.executeRequest(`/hour-registrations/${hourRegistrationId}/accept`, HttpMethod.POST);
    }

    async rejectHourRegistration(hourRegistrationId) {
        return await this.fetcher.executeRequest(`/hour-registrations/${hourRegistrationId}/reject`, HttpMethod.POST);
    }

}