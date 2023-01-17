import User from "./User";
import {UserRole} from "../UserRole";

export default class Admin extends User {

    firstName;
    lastName;

    constructor(id, firstName, lastName, avatar, email) {
        super(id, email, avatar, UserRole.admin);
        this.firstName = firstName;
        this.lastName = lastName;
    }

}