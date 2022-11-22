import {HourRegistration} from "../../components/models/HourRegistration.js";
import FetchService from "../../Services/FetchService.js";

/**
 * Endpoint for retrieving hour registrations for a specialist
 * @author Martijn van der Wal
 */
export class HourRegistrationRepository {
    #fetcher;

    constructor() {
        this.#fetcher = new FetchService('');
    }

    /** Methods **/
    async fetchAllFor(specialistId) {
        try {
            let jsonHrs = await this.#fetcher.fetchUrl(`/users/${specialistId}/hour-registrations/`);
            return jsonHrs.map(HourRegistration.fromJSON)
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    async deleteHourRegistration(id) {
        try {
            return await this.#fetcher.executeDeleteRequestForURL(`/hour-registrations/${id}/delete/`);
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    async fetch(hourRegistrationId) {
        try {
            return this.#fetcher.fetchUrl(`/hour-registrations/${hourRegistrationId}`);
        } catch (e) {
            console.error(e);
        }
    }

    async update(hourRegistrationId, projectId, from, to, description) {
        try {
            return await this.#fetcher.fetchJson(
                `/hour-registrations/${hourRegistrationId}/update`,
                {
                    method: 'PUT',
                    body: JSON.stringify({
                            project_id: projectId,
                            from: from,
                            to: to,
                            description: description
                        }
                    ),
                    headers: {
                        'Content-Type': 'application/json'
                    },
                }
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

        return await this.#fetcher.fetchJsonPost(`/users/${userId}/hour-registrations/`, body);
    }
}