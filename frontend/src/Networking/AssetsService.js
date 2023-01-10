export class AssetsService {

    #baseURL;

    constructor(baseURL = import.meta.env.VITE_BACKEND_URL) {
        this.#baseURL = baseURL;
    }

    /**
     * Gets the asset url from the backend.
     * @param {String} fileName filename from the backend.
     * @return {String} full path to retrieve the asset.
     */
    getAsset(fileName) {
        if (fileName.startsWith("/"))
            fileName = fileName.substring(1);
        return this.getPath("/assets/" + encodeURI(fileName));
    }

    getPath(path) {
        return `${this.#baseURL}/${path}`;
    }
}