import User from "./User";
import {UserRole} from "../UserRole";

export default class Client extends User {

    name;

    constructor(id, name, avatar, email) {
        super(id, email, avatar, UserRole.client);
        this.name = name;
    }

}