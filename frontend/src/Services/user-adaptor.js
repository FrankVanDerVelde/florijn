import FetchService from "./FetchService.js";

export class UserAdaptor extends FetchService {

    constructor() {
        super("auth")
    }

    async asyncFindByCredentials(email, password) {
        console.log('OffersAdaptor.asyncFindByCredentials()...');
        console.log("smash");
        console.log(this.getPath('/login'));
        return this.fetchJsonPost('/login', {
            email: email,
            password: password,
        });
    }
}