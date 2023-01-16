export class Project {

    id
    title
    description
    client

    constructor(id, name, description, client = null) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.client = client;
    }

    static createSample(id) {
        return new Project(id, "Project " + id, "What a cool description, " + id);
    }

}