export class UserAdaptor {
    #resourcesUrl;

    constructor() {
        this.#resourcesUrl = "http://localhost:8086";
    }

    async fetchJson(url, options = null) {
        let response = await fetch(url, options);

        if (response.ok) {
            try {
                return await response.json();
            } catch (e) {
                return null;
            }

        } else {
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    async asyncFindByCredentials(email, password) {
        const userData = await this.fetchJson(this.#resourcesUrl + "/users/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password,
            })
        })
        return userData;
    }
}