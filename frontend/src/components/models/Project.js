export class Project {

    id
    title
    description
    client
    archived
    participants = []

    constructor(id, title, description, client = null, archived = false) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.client = client;
        this.archived = archived;
    }

    static createSample(id) {
        return new Project(id, "Project " + id, "What a cool description, " + id);
    }

}