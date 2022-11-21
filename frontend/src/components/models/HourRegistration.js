import moment from "moment/moment.js";

export class HourRegistration {
    id
    project
    specialistId
    from
    to
    isApproved
    description

    constructor(id, project, specialistId, from, to, isApproved, description) {
        this.id = id;
        this.project = project;
        this.specialistId = specialistId;
        this.from = moment(from).toDate();
        this.to = moment(to).toDate();
        this.isApproved = isApproved;
        this.description = description;
    }

    static fromJSON(object) {
        return new HourRegistration(
            object.id,
            object.project,
            object.specialistId,
            object.from,
            object.to,
            object.description
        )
    }

    formattedFromToTime() {
        const from = new Date(this.from);
        const to = new Date(this.to);

        let options = { hour: "2-digit", minute: "2-digit" };
        console.log(typeof from);
        const fromFormatted = from.toLocaleTimeString('nl-nl', options);
        const toFormatted = to.toLocaleTimeString('nl-nl', options);

        return `${fromFormatted} - ${toFormatted}`;
    }
}