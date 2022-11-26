import FetchService from "./FetchService";

export class UserAdaptor extends FetchService {

    constructor() {
        super("");
    }

    async asyncFindByCredentials(email, password) {
        return await this.fetchJsonPost("/login", {
            email: email,
            password: password
        });
    }
}