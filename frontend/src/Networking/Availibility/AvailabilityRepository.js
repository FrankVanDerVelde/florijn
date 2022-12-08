import FetchService from "../../Services/FetchService.js";
import {Availability} from "../../components/models/Availability.js";

export class AvailabilityRepository {

    fetcher;

    constructor() {
        this.fetcher = new FetchService('');
    }

    /**
     * Fetches a list of Availability for the specified week
     * @param userId
     * @param weekNumber
     * @return {Promise<[Availability]>}
     */
    async fetchAvailabilityForUserInWeek(userId, weekNumber) {
        try {
            let results = await this.fetcher.fetchJson(`/users/${userId}/availability`);
            return results.map(Availability.fromJSON);
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    /**
     * create a new availability
     * @param userId
     * @param date
     * @param fromTime
     * @param toTime
     * @return {Promise<*|null>}
     */
    async createAvailability(userId, date, fromTime, toTime) {
        try {
            let result = await this.fetcher.fetchJsonMethod(
                `/users/${userId}/availability`,
                HttpMethods.POST,
                {
                    date: date,
                    from: fromTime,
                    to: toTime
                }
            );

            return Availability.fromJSON(result);
        } catch (e) {
            console.error(e);
            return e;
        }
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
        try {
            let result = await this.fetcher.fetchJsonMethod(
                `/availability/${id}/update`,
                HttpMethods.PUT,
                {
                    date: date,
                    from: fromTime,
                    to: toTime
                }
            );
            return Availability.fromJSON(result);
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    /**
     * Deletes an availability
     * @param id
     * @return {Promise<Availability>}
     */
    async deleteAvailability(id) {
        try {
            let result = await this.fetcher.fetchJsonMethod(`/availability/${id}/delete`, HttpMethods.DELETE);
            return Availability.fromJSON(result);
        } catch (e) {
            console.error(e);
            return e;
        }
    }

    /**
     * Gets the availability
     * @param id
     * @return {Promise<*|null>}
     */
    async getAvailability(id) {
        try {
            let result = await this.fetcher.fetchJsonMethod(`/availability/${id}`, HttpMethods.GET);
            return Availability.fromJSON(result);
        } catch (e) {
            console.error(e);
            return e;
        }
    }
}