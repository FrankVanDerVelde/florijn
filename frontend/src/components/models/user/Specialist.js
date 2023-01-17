import {UserRole} from "../UserRole";
import User from "./User";

export default class Specialist extends User{

    firstName;
    lastName;

    constructor(id, firstName, lastName, avatar, email) {
        super(id, email, avatar, UserRole.specialist);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    static createDummy(id) {
        const sampleFirstNames = ["Henk", "Lisa", "Kylie", "Samira", "Pedro"];
        const sampleLastNames = ["de Jong", "Gonzales", "de Vogel", "de Berg", "de Wilde"];
        const sampleRole = ["SPECIALIST", "ADMIN", "CLIENT"];

        const chosenRole = sampleRole[Math.floor(Math.random()*sampleRole.length)]

        return new Specialist(
            id,
            sampleFirstNames[Math.floor(Math.random() * sampleFirstNames.length)],
            sampleLastNames[Math.floor(Math.random() * sampleLastNames.length)],
            chosenRole,
            "6.png",
            `${sampleRole}${id}@test.com`
        );
    }
}