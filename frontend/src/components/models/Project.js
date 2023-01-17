export class Project {

    id
    title
    description
    client

    constructor(id, title, description, client = null, participants = []) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.client = client;
        this.participants = participants;
    }

    static createSample(id) {
        return new Project(id, "Project " + id, "What a cool description, " + id, null, []);
    }

}