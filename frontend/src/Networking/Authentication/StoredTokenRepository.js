export class StoredTokenRepository {
    #BROWSER_STORAGE_ITEM_NAME;
    #token
    #user

    constructor(browserStorageItemName) {
        this.#token = null;
        this.#user = null;
        this.#BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
    }

    saveToken(token, user) {
        this.#token = token;
        this.#user = user;

        if (token == null) {
            this.#user = null;
            window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        } else {
            console.log("New token for " + user.name + ": " + token);
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, token);
            window.localStorage.setItem("user", user);
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