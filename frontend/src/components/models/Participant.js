export default class Participant {

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

    static createDummy(id) {
        const sampleFirstNames = ["Henk", "Lisa", "Kylie", "Samira", "Pedro"];
        const sampleLastNames = ["de Jong", "Gonzales", "de Vogel", "de Berg", "de Wilde"];
        const sampleRole = ["SPECIALIST", "ADMIN", "CLIENT"];

        const chosenRole = sampleRole[Math.floor(Math.random()*sampleRole.length)]

        const newParticipant = new Participant(
            id,
            sampleFirstNames[Math.floor(Math.random()*sampleFirstNames.length)],
            sampleLastNames[Math.floor(Math.random()*sampleLastNames.length)],
            chosenRole,
            "6.png",
            `${sampleRole}${id}@test.com`
        );

        return newParticipant;
    }
}