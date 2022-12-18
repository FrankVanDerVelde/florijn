import {HttpMethod} from "./HttpMethod.js";
export class NetworkClient {

    #baseURL;
    #storedTokenRepository;

    constructor(baseURL = import.meta.env.VITE_BACKEND_URL, storedTokenRepository) {
        this.#baseURL = baseURL;
        this.#storedTokenRepository = storedTokenRepository;
    }

    /**
     * Sends a network request to the backen
     * @param {String} path The path to be appended after the base URL.
     * @param {HttpMethod} method Specify the HttpMethod to use. Defaults to HttpMethod.GET.
     * @param {Object} body Body to use with the request, null by default.
     * @param {Object} headers Specify header fields you want to include.
     * @param {RequestInit} options Native JavaScript fetch options. Do not specify the `header` field in the options, use the `headers` parameter from this.
     * @return {Promise<[Object]|Object|String>} return value.
     */
    async executeRequest(
        path,
        method = HttpMethod.GET,
        body = null,
        headers = null,
        options = null
    ) {
        let finalOptions = this.#getRequestOptionsForMethod(method, body, headers, options);
        const url = this.#createURL(path);
        this.#logFetchConfiguration(method, url, finalOptions.body, finalOptions);
        try {
            let response = await fetch(url, finalOptions);

            if (response.ok) {
                const data = await response.json();
                this.#logNetworkResponse(response, data);

                if (response.headers.has("Authorization"))
                    data.token = response.headers.get("Authorization");

                return data;
            } else {
                throw {
                    message: `NetworkClient - response with non 200 http code`,
                    response: response,
                    statusCode: response.status
                }
            }
        } catch (error) {
            this.#logNetworkError(method, path, finalOptions, error);
            throw error;
        }
    }

    /**
     * Merges options and returns the final `RequestInit` object.
     * @param {HttpMethod} method method to use for the request.
     * @param {Object|null} body Object body, will be turned into a String.
     * @param {Object|null} headers headers to include, giving a value will override the default headers.
     * @param {RequestInit} options additional options to provide that will be used in the return value.
     * @return {RequestInit} merged request options.
     */
    #getRequestOptionsForMethod(method, body, headers, options) {
        const defaultOptions = {
            method: method,
            headers: this.#signHeaderWithToken(headers ?? this.getDefaultHeader()),
            ...options
        }
        if (this.#supportsBodyForHttpMethod(method) && body) {
            return {
                body: JSON.stringify(body),
                ...defaultOptions
            }
        } else {
            return defaultOptions;
        }
    }

    #supportsBodyForHttpMethod(method) {
        return method === HttpMethod.POST || method === HttpMethod.PUT;
    }

    #logFetchConfiguration(method, url, body, options) {
        console.log(`NetworkClient - ${method} ${url}\nbody: %o\noptions: %o`, body, options);
    }

    #logNetworkResponse(response, data) {
        console.log(`NetworkClient - status code: ${response.status} response: %o`, data);
    }

    #logNetworkError(method, url, options, error) {
        console.error(`NetworkClient - request failed for request: ${method} ${url}\nwith options: %o.\nerror: %o`, options, error);
    }

    /**
     * Creates a URL by combining the base URL and the path;
     * @param {String} path path to be appended after the base URL.
     */
    #createURL(path) {
        let correctedPath = path;
        if (path.charAt(0) !== '/') {
            correctedPath = `/${path}`;
        }
        return `${this.#baseURL}${correctedPath}`;
    }

    getDefaultHeader() {
        return this.#signHeaderWithToken({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        });
    }

    /**
     * Signs the header with a JWT Authorization Token.
     * @param {Object} headerFields Header fields going in.
     * @return {*&{Authorization: string}} Signed header with the ingoing header options and the authorization header if available.
     */
    #signHeaderWithToken(headerFields) {
        const token = this.#storedTokenRepository?.getToken();
        if (token) {
            return {
                'Authorization': `Bearer ${token}`,
                ...headerFields
            }
        } else {
            return headerFields;
        }
    }
}
