import {HttpMethod} from "./HttpMethod.js";
import {StoredTokenRepository} from "./Authentication/StoredTokenRepository.js";
export class NetworkClient {

    #baseURL;
    #storedTokenRepository;
    #loggingEnabled

    constructor(baseURL = import.meta.env.VITE_BACKEND_URL) {
        this.#baseURL = baseURL;
        this.#storedTokenRepository = StoredTokenRepository.shared;
        this.#loggingEnabled = false;
    }

    /**
     * Sends a network request to the backend.
     * @param {String} path The path to be appended after the base URL.
     * @param {HttpMethod} method Specify the HttpMethod to use. Defaults to HttpMethod.GET.
     * @param {Object|null} body Body to use with the request, null by default.
     * @param {Object|null} headers Specify header fields you want to include.
     * @param {RequestInit|null} options Native JavaScript fetch options. Do not specify the `header` field in the options, use the `headers` parameter from this.
     * @return {Promise<[Object]|Object|String>} return value.
     */
    async executeRequest(
        path,
        method = HttpMethod.GET,
        body = null,
        headers = null,
        options = null
    ) {
        let finalOptions = this.#getRequestOptionsForMethod(
            method,
            body ? JSON.stringify(body) : null,
            headers ?? this.getDefaultHeader(),
            options
        );
        return await this.#executeNetworkRequestWithOptions(finalOptions, path, method);
    }

    /**
     * Sends a network request to the backend using FormData.
     * @param {String} path The path to be appended after the base URL.
     * @param {HttpMethod} method Specify the HttpMethod to use. Defaults to HttpMethod.POST.
     * @param {FormData} body FormData Body to use with the request, will NOT stringify body.
     * @param {Object|null} headers Specify header fields you want to include.
     * @param {RequestInit|null} options Native JavaScript fetch options. Do not specify the `header` field in the options, use the `headers` parameter from this.
     * @return {Promise<[Object]|Object|String>} return value.
     */
    async executeRequestWithFormData(
        path,
        method = HttpMethod.POST,
        body = null,
        headers = null,
        options = null
    ) {
        let finalOptions = this.#getRequestOptionsForMethod(
            method,
            body,
            {},
            options
        );
        return await this.#executeNetworkRequestWithOptions(finalOptions, path, method);
    }

    /**
     * Merges options and returns the final `RequestInit` object.
     * @param {HttpMethod} method method to use for the request.
     * @param {Object|String|null} body Object body, can be a JSON turned into a String.
     * @param {Object|null} headers headers to include, giving a value will override the default headers.
     * @param {RequestInit|null} options additional options to provide that will be used in the return value.
     * @return {RequestInit} merged request options.
     */
    #getRequestOptionsForMethod(method, body, headers, options) {
        const defaultOptions = {
            method: method,
            headers: this.#signHeaderWithToken(headers),
            ...options
        }
        if (this.#supportsBodyForHttpMethod(method) && body) {
            return {
                body: body,
                ...defaultOptions
            }
        } else {
            return defaultOptions;
        }
    }

    /**
     * Executes request with determined options and return value from backend or an error.
     * @param {RequestInit} options Request options
     * @param {String} path Path used for the request
     * @param {HttpMethod} method HTTP Method used
     * @return {Promise<any>} result type.
     */
    async #executeNetworkRequestWithOptions(options, path, method) {
        const url = this.#createURL(path);
        this.#logFetchConfiguration(method, url, options.body, options);
        try {
            let response = await fetch(url, options);

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
            this.#logNetworkError(method, path, options, error);
            throw error;
        }
    }

    #supportsBodyForHttpMethod(method) {
        return method === HttpMethod.POST || method === HttpMethod.PUT;
    }

    #logFetchConfiguration(method, url, body, options) {
        if (this.#loggingEnabled) {
            console.log(`NetworkClient - ${method} ${url}\nbody: %o\noptions: %o`, body, options);
        }
    }

    #logNetworkResponse(response, data) {
        if (this.#loggingEnabled) {
            console.log(`NetworkClient - status code: ${response.status} response: %o`, data);
        }
    }

    #logNetworkError(method, url, options, error) {
        if (this.#loggingEnabled) {
            console.error(`NetworkClient - request failed for request: ${method} ${url}\nwith options: %o.\nerror: %o`, options, error);
        }
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
     * @param {Object|null} headerFields Header fields going in.
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
            if (this.#loggingEnabled) {
                console.warn("NetworkClient signHeaderWithToken: No JWT Token found.")
                // console.log(this.#storedTokenRepository);
            }
            return headerFields;
        }
    }
}
