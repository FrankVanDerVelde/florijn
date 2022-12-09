export class Availability {
    id;
    user;
    date;
    from;
    to;

    constructor(id, user, date, from, to) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.from = from;
        this.to = to;
    }
}