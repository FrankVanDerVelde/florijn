import FetchService from "../../Services/FetchService.js";

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
            return await this.fetcher.fetchJson(`/users/${userId}/availability`);
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
            return await this.fetcher.fetchJsonMethod(
                `/users/${userId}/availability`,
                HttpMethods.POST,
                {
                    date: date,
                    from: fromTime,
                    to: toTime
                }
            );
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
            return await this.fetcher.fetchJsonMethod(
                `/availability/${id}/update`,
                HttpMethods.PUT,
                {
                    date: date,
                    from: fromTime,
                    to: toTime
                }
            );
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
            return await this.fetcher.fetchJsonMethod(`/availability/${id}/delete`, HttpMethods.DELETE);
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
            return await this.fetcher.fetchJsonMethod(`/availability/${id}`, HttpMethods.GET);
        } catch (e) {
            console.error(e);
            return e;
        }
    }
}