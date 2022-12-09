import FetchService from "../../Services/FetchService.js";
import {Availability} from "../../components/models/Availability.js";
import {HttpMethods} from "../HttpMethods.js";

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
            let results = await this.fetcher.fetchJson(`/users/${userId}/availability?weekNumber=${weekNumber}`);
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
            let body = {
                date: date,
                from: fromTime,
                to: toTime
            };
            console.log(body);
            let result = await this.fetcher.fetchJsonMethod(
                `/users/${userId}/availability`,
                HttpMethods.POST,
                body
            );

            console.log(result);

            return Availability.fromJSON(result);
        } catch (e) {
            console.error(e);
            throw e;
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
            let body = {
                date: date,
                from: fromTime,
                to: toTime
            };
            console.log(`updateAvailability body: ${body}`);
            let result = await this.fetcher.fetchJsonMethod(
                `/availability/${id}/update`,
                HttpMethods.PUT,
                body
            );
            return Availability.fromJSON(result);
        } catch (e) {
            console.error(e);
            throw e;
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
            throw e;
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
            throw e;
        }
    }

    async copyToWeek(userId, weekNumber) {
        try {
            let result = await this.fetcher.fetchJsonMethod(
                `/users/${userId}/availability/weeks/${weekNumber}/set-on-next-week`,
                HttpMethods.POST
            );
            return result.map(Availability.fromJSON);
        } catch (e) {
            console.log(e);
            throw e;
        }
    }
}