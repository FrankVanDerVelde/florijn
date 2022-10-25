export class HourRegistration {
    #id
    #project
    #specialistId
    #from
    #to
    #isApproved
    #description

    constructor(id, project, specialistId, from, to, isApproved, description) {
        this.#id = id;
        this.#project = project;
        this.#specialistId = specialistId;
        this.#from = from;
        this.#to = to;
        this.#isApproved = isApproved;
        this.#description = description;
        this._id = id;
        this._project = project;
        this._specialistId = specialistId;
        this._from = from;
        this._to = to;
        this._isApproved = isApproved;
        this._description = description;
    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    get project() {
        return this._project;
    }

    set project(value) {
        this._project = value;
    }

    get specialistId() {
        return this._specialistId;
    }

    set specialistId(value) {
        this._specialistId = value;
    }

    get from() {
        return this._from;
    }

    formattedFromToTime() {
        const from = this._from;
        const to = this._to;

        let options = { hour: "2-digit", minute: "2-digit" };
        const fromFormatted = from.toLocaleTimeString('nl-nl', options);
        const toFormatted = to.toLocaleTimeString('nl-nl', options);

        return `${fromFormatted} - ${toFormatted}`;
    }

    set from(value) {
        this._from = value;
    }

    get to() {
        return this._to;
    }

    set to(value) {
        this._to = value;
    }

    get isApproved() {
        return this._isApproved;
    }

    set isApproved(value) {
        this._isApproved = value;
    }

    get description() {
        return this._description;
    }

    set description(value) {
        this._description = value;
    }
}