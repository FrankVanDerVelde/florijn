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

    /**
     * Turns the JSON object from the server into a `Availability` object.
     * @param item as JSON from the server.
     */
    static fromJSON(item) {
        let year = item.date[0];
        let month = item.date[1] - 1;
        let day = item.date[2];

        let fromHour = item.from[0];
        let fromMinutes = item.from[1];

        let toHours = item.to[0];
        let toMinutes = item.to[1];

        let date = new Date(year, month, day);
        let from = new Date(year, month, day, fromHour, fromMinutes);
        let to = new Date(year, month, day, toHours, toMinutes);

        return new Availability(item.id, item.user, date, from, to);
    }
}