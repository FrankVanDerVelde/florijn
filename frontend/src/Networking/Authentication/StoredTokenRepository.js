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
        console.log(this.#user);
        return this.#user;
    }
}