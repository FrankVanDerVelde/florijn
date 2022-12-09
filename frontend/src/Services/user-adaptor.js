export class UserAdaptor {
    #resourcesUrl;

    constructor() {
        this.#resourcesUrl = import.meta.env.VITE_VUE_APP_API_URL;
        console.log("Created UserAdaptor " + this.#resourcesUrl);
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
        console.log('OffersAdaptor.asyncFindByCredentials()...');
        const userData = await this.fetchJson(this.#resourcesUrl + "/login", {
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