import CONFIG from "../../../config.js";

export class StoredTokenRepository {
    #BROWSER_STORAGE_ITEM_NAME;
    #token
    #user

    constructor(browserStorageItemName) {
        this.#BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.#user = null;
        this.#token = null;
        this.refreshTokenBrowserStorage();
    }

    static shared = new StoredTokenRepository(CONFIG.JWT_STORAGE_ITEM);

    saveToken(token, user) {
        this.#token = token;
        this.#user = user;

        if (token == null) {
            this.#user = null;
            window.sessionStorage.removeItem(this.#BROWSER_STORAGE_ITEM_NAME);
            window.localStorage.removeItem(this.#BROWSER_STORAGE_ITEM_NAME);
            window.localStorage.removeItem("user");
        } else {
            console.log("New token for " + user.firstName + ": " + token);
            window.sessionStorage.setItem(this.#BROWSER_STORAGE_ITEM_NAME, token);
            window.localStorage.setItem(this.#BROWSER_STORAGE_ITEM_NAME, token);
            window.localStorage.setItem("user", JSON.stringify(user));
        }
    }

    refreshTokenBrowserStorage(){
        const user = window.localStorage.getItem("user");
        let token = window.sessionStorage.getItem(this.#BROWSER_STORAGE_ITEM_NAME);

        if (user != null) this.#user = JSON.parse(user);
        if (token == null) {
            token = window.localStorage.getItem(this.#BROWSER_STORAGE_ITEM_NAME);
            window.sessionStorage.setItem(this.#BROWSER_STORAGE_ITEM_NAME, token)
        }
        this.#token = token;
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