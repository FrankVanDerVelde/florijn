import moment from "moment/moment.js";

export class HourRegistration {
    id
    project
    participant
    from
    to
    isApproved
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

    static fromJSON(object) {
        return new HourRegistration(
            object.id,
            object.project,
            object.participant,
            object.from,
            object.to,
            object.description,
            object.status
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