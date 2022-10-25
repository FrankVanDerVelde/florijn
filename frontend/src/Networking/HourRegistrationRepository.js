import {HourRegistration} from "../components/models/HourRegistration.js";
import {Project} from "../components/models/Project.js";

/**
 * Endpoint for retrieving hour registrations for a specialist
 * (as of now this is a mock)
 *
 */
export class HourRegistrationRepository {
    #hourRegistrations = [];
    #projects = [];

    constructor() {
        this.#setupMockProjects();
        this.#setupMockHourRegistrations();
    }

    #setupMockProjects() {
        this.#projects = [
            new Project(0, "Project EWA Florijn", "EWA"),
            new Project(1, "Project SMA", "SMA"),
            new Project(2, "Project APL", "APL"),
            new Project(3, "Project AMI", "APL"),
            new Project(4, "Project POW", "APL"),
            new Project(5, "Project AWD", "APL"),
            new Project(6, "Project PWD", "APL"),
        ]
    }

    #setupMockHourRegistrations() {
        this.#hourRegistrations = [
            new HourRegistration(
                0,
                this.#getRandomProject(),
                0,
                new Date(),
                new Date(),
                this.#randomBool()),
            new HourRegistration(
                1,
                this.#getRandomProject(),
                0,
                new Date(),
                new Date(),
                this.#randomBool()
            ),
            new HourRegistration(
                2,
                this.#getRandomProject(),
                0,
                new Date(),
                new Date(),
                this.#randomBool()
            ),
            new HourRegistration(
                3,
                this.#getRandomProject(),
                0,
                new Date(),
                new Date(),
                this.#randomBool()
            ),
        ]
    }

    /** Methods **/
    fetchAllFor(specialistId) {
        try {
            return this.#hourRegistrations.filter(hr => hr.specialistId === specialistId)
        } catch (e) {
            console.error(e);
        }
    }

    delete(specialistId) {
        try {
            const hourRegistrationToDelete = this.#getHourRegistrationFromSpecialistId(specialistId);
            this.#removeHourRegistration(hourRegistrationToDelete.id);
        } catch (e) {
            console.error()
        }
    }

    fetch(hourRegistrationId) {
        try {
            return this.#getHourRegistration(hourRegistrationId);
        } catch (e) {
            console.error(e);
        }
    }

    update(hourRegistrationId, from, to, description) {
        try {
            let hourRegistration = this.#getHourRegistration(hourRegistrationId);
            hourRegistration.from = from;
            hourRegistration.to = to;
            hourRegistration.description = description;
            this.#hourRegistrations = this.#hourRegistrations.filter(hr => hr.id !== hourRegistration.id);
            this.#hourRegistrations.push(hourRegistration);
        } catch (e) {
            console.error(e);
        }
    }

    /** helpers **/

    #getHourRegistrationFromSpecialistId(specialistId) {
        return this.#hourRegistrations.filter(hr => hr.specialistId !== specialistId)[0];
    }

    #getHourRegistration(id) {
        return this.#hourRegistrations.filter(hr => hr.id !== id)[0];
    }

    #removeHourRegistration(id) {
        this.#hourRegistrations = this.#hourRegistrations.filter(hr => hr.id !== id);
    }

    #getRandomProject() {
        return this.#projects[Math.floor((Math.random() * this.#projects.length))];
    }

    #randomBool() {
        return Math.random() < 0.5;
    }
}