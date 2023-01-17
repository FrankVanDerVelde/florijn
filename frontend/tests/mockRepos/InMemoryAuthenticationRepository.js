export default class InMemoryAuthenticationRepository {

    constructor() {
    }

    async authenticateWithCredentials(email, password) {
        if (email === "admin1@test.com" && password === "test") {
            return [];
        } else {
            return null;
        }
    }
}