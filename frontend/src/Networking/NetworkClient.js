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
     * @param {Object} options Native JavaScript fetch options. Do not specify the `header` field in the options, use the `headers` parameter from this.
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

    #getRequestOptionsForMethod(method, body, headers, options) {
        const defaultOptions = {
            method: method,
            header: this.#signHeaderWithToken(headers ?? this.#getDefaultHeader()),
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

    #getDefaultHeader() {
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
