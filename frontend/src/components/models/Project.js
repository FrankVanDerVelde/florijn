export class Project {
    #id
    #name
    #description

    constructor(id, name, description) {
        this.#id = id;
        this.#name = name;
        this.#description = description;
        this._id = id;
        this._name = name;
        this._description = description;
    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get description() {
        return this._description;
    }

    set description(value) {
        this._description = value;
    }
}