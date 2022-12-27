import FetchService from "../../Services/FetchService.js";
import {Availability} from "../../components/models/Availability.js";
import {HttpMethod} from "../HttpMethod.js";
import {NetworkClient} from "../NetworkClient.js";

export class AvailabilityRepository {

    #networkClient;

    constructor() {
        this.#networkClient = new NetworkClient();
    }

    /**
     * Fetches a list of Availability for the specified week
     * @param userId
     * @param weekNumber
     * @return {Promise<[Availability]>}
     */
    async fetchAvailabilityForUserInWeek(userId, weekNumber) {
        const path = `/users/${userId}/availability?weekNumber=${weekNumber}`;
        let results = await this.#networkClient.executeRequest(path, HttpMethod.GET);
        return results.map(Availability.fromJSON);
    }

    /**
     * create a new availability
     * @param userId
     * @param date
     * @param fromTime
     * @param toTime
     * @return {Promise<Availability|null>}
     */
    async createAvailability(userId, date, fromTime, toTime) {
        const path = `/users/${userId}/availability`;
        const body = {
            date: date,
            from: fromTime,
            to: toTime
        };
        let result = await this.#networkClient.executeRequest(path, HttpMethod.POST, body);
        return Availability.fromJSON(result);
    }

    /**
     * Updates an availability
     * @param id
     * @param date
     * @param fromTime
     * @param toTime
     * @return {Promise<*|null>}
     */
    async updateAvailability(id, date, fromTime, toTime) {
        const path = `/availability/${id}/update`;
        let body = {
            date: date,
            from: fromTime,
            to: toTime
        };
        let result = await this.#networkClient.executeRequest(path, HttpMethod.PUT, body);
        return Availability.fromJSON(result);
    }

    /**
     * Deletes an availability
     * @param id
     * @return {Promise<Availability>}
     */
    async deleteAvailability(id) {
        const path = `/availability/${id}/delete`;
        let result = await this.#networkClient.executeRequest(path, HttpMethod.DELETE);
        return Availability.fromJSON(result);
    }

    /**
     * Gets the availability
     * @param id
     * @return {Promise<Availability|null>}
     */
    async getAvailability(id) {
        const path = `/availability/${id}`;
        let result = await this.#networkClient.executeRequest(path);
        return Availability.fromJSON(result);
    }

    async copyToWeek(userId, weekNumber) {
        const path = `/users/${userId}/availability/weeks/${weekNumber}/set-on-next-week`;
        let result = await this.#networkClient.executeRequest(path, HttpMethod.POST);
        return result.map(Availability.fromJSON);
    }
}