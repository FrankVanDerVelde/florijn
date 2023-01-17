export default class User {

    id;
    email;
    avatar;
    role;

    constructor(id, email, avatarUrl, role) {
        this.id = id;
        this.email = email;
        this.avatar = avatarUrl;
        this.role = role;
    }

}