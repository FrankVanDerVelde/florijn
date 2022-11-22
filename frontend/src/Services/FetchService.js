export default class FetchService {

    #domain;
    #endpoint;

    constructor(endpoint, domain = 'http://localhost:8086') {
        this.#endpoint = endpoint;
        this.#domain = domain;
    }

    #getPath(path) {
        if (this.#endpoint.length === 0) return `${this.#domain}${path}`;
        return `${this.#domain}/${this.#endpoint}${path}`;
    }

    async fetchJson(path, options = null) {
        let response = await fetch(this.#getPath(path), {
            headers: {
                'Accept': 'application/json',
            },
            ...options
        });
        if (response.ok) {
            return await response.json();
        }

        console.log(response, !response.bodyUsed ? await response.text() : "")
        return null;
    }

    async fetchJsonPost(path, body = {}, options = {}) {
        return this.fetchJson(path, {
            method: "POST",
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json'
            },
            ...options
        });
    }

    async fetchUrl(path, method = "GET", options = {}) {
        return this.fetchJson(path, {
            method: method,
            ...options
        });
    }

    async fetchJsonUrl(path, method = "GET", options = {}) {
        return this.fetchUrl(path, method, {
            headers: {
                'Accept': 'application/json',
            },
            ...options
        });
    }

    async executeDeleteRequestForURL(path, options = {}) {
        return this.fetchJson(path, {
            method: "DELETE",
            ...options
        });
    }

}