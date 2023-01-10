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
     * @param {Number} userId user's id
     * @param {Number} weekNumber week number
     * @param {Number} year year
     * @return {Promise<[Availability]>} list of availabilities on the specified week.
     */
    async fetchAvailabilityForUserInWeek(userId, weekNumber, year) {
        let queryOptions = {
            weekNumber: weekNumber,
            year: year
        }
        const queryOptionString = new URLSearchParams(queryOptions).toString();
        const path = `/users/${userId}/availability?${queryOptionString}`;
        let results = await this.#networkClient.executeRequest(path, HttpMethod.GET);
        return results.map(Availability.fromJSON);
    }

    /**
     * create a new availability
     * @param {Number} userId
     * @param {String} date Formatted date using YYYY-MM-dd format (like: 2022-03-24 for 2022 March the 24th.)
     * @param {String} fromTime Formatted time using HH:mm format (like: 12:30)
     * @param {String} toTime Formatted time using HH:mm format (like: 12:30)
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
     * @param {Number} id user's id
     * @param {String} date Formatted date using YYYY-MM-dd format/ (like: 2022-03-24 for 2022 March the 24th.)
     * @param {String} fromTime Formatted time using HH:mm format.
     * @param {String} toTime  Formatted time using HH:mm format.
     * @return {Promise<Availability|null>} updated Availability.
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
     * @param {Number} id id of the Availability.
     * @return {Promise<Availability>} deleted Availability.
     */
    async deleteAvailability(id) {
        const path = `/availability/${id}/delete`;
        let result = await this.#networkClient.executeRequest(path, HttpMethod.DELETE);
        return Availability.fromJSON(result);
    }

    /**
     * Gets the availability
     * @param {Number} id id of the Availability.
     * @return {Promise<Availability|null>} Availability.
     */
    async getAvailability(id) {
        const path = `/availability/${id}`;
        let result = await this.#networkClient.executeRequest(path);
        return Availability.fromJSON(result);
    }

    /**
     * Copies the specified week number to the next week.
     * @param {Number} userId The user's id.
     * @param {Number} weekNumber week number of the year that you want to copy from. (1...51)
     * @param {Number} year year number you want to copy from.
     * @return {Promise<[Availability]>} List of availabilities for next week.
     */
    async copyToWeek(userId, weekNumber, year) {
        let queryOptions = {
            weekNumber: weekNumber,
            year: year
        }
        const queryOptionString = new URLSearchParams(queryOptions).toString();
        const path = `/users/${userId}/availability/set-on-next-week?${queryOptionString}`;
        let result = await this.#networkClient.executeRequest(path, HttpMethod.POST);
        return result.map(Availability.fromJSON);
    }
}