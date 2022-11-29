export default class FetchService {

    #domain;
    #endpoint;

    constructor(endpoint, domain = import.meta.env.VITE_BACKEND_URL) {
        this.#endpoint = endpoint;
        this.#domain = domain;
    }

    getPath(path) {
        if (this.#endpoint.length === 0) return `${this.#domain}${path}`;
        return `${this.#domain}/${this.#endpoint}${path}`;
    }

    getAsset(path) {
        if (path.startsWith("/")) path = path.substring(1);

        return this.getPath("/assets/" + encodeURI(path));
    }

    async fetchJson(path, options = null) {
        let response = await fetch(this.getPath(path), {
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
        return this.fetchJsonMethod(path, "POST", body, options);
    }

    async fetchJsonMethod(path, method = "GET", body = {}, options = {}) {
        if (body instanceof Object) body = JSON.stringify(body);

        return this.fetchJson(path, {
            method: method,
            body: body,
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

    async fetchJsonFile(path, method = "POST", formData, options = {}) {
        return this.fetchUrl(path, method, {
            body: formData
        })
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