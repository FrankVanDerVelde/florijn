export class Project {

    id
    title
    description
    client
    archived

    constructor(id, title, description, client = null, participants = []) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.client = client;
        this.archived = archived;
        this.participants = participants;
    }

    static createSample(id) {
        return new Project(id, "Project " + id, "What a cool description, " + id, null, []);
    }

}