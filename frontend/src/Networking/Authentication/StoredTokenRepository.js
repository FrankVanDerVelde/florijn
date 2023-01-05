import CONFIG from "../../../config.js";

export class StoredTokenRepository {
    #BROWSER_STORAGE_ITEM_NAME;
    #token
    #user

    constructor(browserStorageItemName) {
        this.#BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
    }

    static shared = new StoredTokenRepository(CONFIG.JWT_STORAGE_ITEM);

    saveToken(token, user) {
        this.#token = token;
        this.#user = user;

        if (token == null) {
            this.#user = null;
            window.sessionStorage.removeItem(this.#BROWSER_STORAGE_ITEM_NAME);
        } else {
            console.log("New token for " + user.firstName + ": " + token);
            window.sessionStorage.setItem(this.#BROWSER_STORAGE_ITEM_NAME, token);
        }
    }

    signOut() {
        this.saveToken(null, null);
    }

    isAuthenticated() {
        return this.#token != null;
    }

    getToken() {
        return this.#token;
    }

    getUser() {
        return this.#user;
    }
}