class Participant {

    #id;
    #firstName;
    #lastName;
    #role;
    #avatar;
    #email;

    constructor(id, firstName, lastName, role, avatar, email) {
        this.#id = id;
        this.#firstName = firstName;
        this.#lastName = lastName;
        this.#role = role;
        this.#avatar = avatar;
        this.#email = email;
    }

    get id() {
        return this.#id;
    }

    get firstName() {
        return this.#firstName;
    }

    set firstName(value) {
        this.#firstName = value;
    }

    get lastName() {
        return this.#lastName;
    }

    set lastName(value) {
        this.#lastName = value;
    }

    get role() {
        return this.#role;
    }

    set role(value) {
        this.#role = value;
    }

    get avatar() {
        return this.#avatar;
    }

    set avatar(value) {
        this.#avatar = value;
    }

    get email() {
        return this.#email;
    }

    set email(value) {
        this.#email = value;
    }
}