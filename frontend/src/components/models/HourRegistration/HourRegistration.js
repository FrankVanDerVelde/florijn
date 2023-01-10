import moment from "moment/moment.js";
import {HourRegistrationStatus} from "./HourRegistrationStatus.js";

export class HourRegistration {
    id
    project
    participant
    from
    to
    description
    status

    constructor(id, project, participant, from, to, description, status) {
        this.id = id;
        this.project = project;
        this.participant = participant;
        this.from = moment(from).toDate();
        this.to = moment(to).toDate();
        this.description = description;
        this.status = status;
    }

    /**
     * Creates a HourRegistration object from the JSON coming in.
     * If this fails it returns the input object.
     * @param {Object} object JSON representation of a HourRegistration
     * @return {HourRegistration|Object} HourRegistration object, or input object when it failed to convert.
     */
    static fromJSON(object) {
        try {
            return new HourRegistration(
                object.id,
                object.project,
                object.participant,
                object.from,
                object.to,
                object.description,
                object.status
            );
        } catch (e) {
            console.error(e);
            console.log("Failed to cover JSON Object to HourRegistration object, returning input value.");
            return object;
        }
    }

    /**
     * Formats the time of the HourRegistration's `from` and `to` values.
     * @return {string} Formatted time string, format: "HH:mm - HH:mm".
     */
    formattedFromToTime() {
        const from = new Date(this.from);
        const to = new Date(this.to);

        let options = { hour: "2-digit", minute: "2-digit" };

        const fromFormatted = from.toLocaleTimeString('nl-nl', options);
        const toFormatted = to.toLocaleTimeString('nl-nl', options);

        return `${fromFormatted} - ${toFormatted}`;
    }

    isApproved() {
        return this.status === HourRegistrationStatus.accepted;
    }

    isRejected() {
        return this.status === HourRegistrationStatus.rejected;
    }

    /**
     * A HourRegistration is locked when it has either been approved or rejected.
     */
    isLocked() {
        return this.isApproved() || this.isRejected();
    }
}