import InMemoryEntitiesService from "./InMemoryEntitiesService";
import Admin from "../../src/components/models/user/Admin";
import Specialist from "../../src/components/models/user/Specialist";
import Client from "../../src/components/models/user/Client";

export default class InMemoryUserRepo extends InMemoryEntitiesService {

    constructor() {
        super();
        this.#createSampleUsers()
    }

    #createSampleUsers() {
        for (let i = 0; i < 10; i++) {
            super.entities.push(new Admin(i, ("Test" + i), ("Surname" + i), null, ("Admin" + i + "test.com")));
            super.entities.push(new Specialist((i+10), ("Test" + i), ("Surname" + i), null, ("Specialist" + i + "test.com")));
            super.entities.push(new Client((i+20), ("Test" + i), ("Surname" + i), null, ("Client" + i + "test.com")));
        }
    }

    static shared = new InMemoryUserRepo();

    fetchUsers(role) {
        return super.entities.filter(user => user.role === role);
    }

    findAll() {
        return this._entities;
    }

    getUserById(id) {
        return this._entities.find(e => e.id === id);
    }

    save(entity) {
        if (entity == null) return;
        const index = this._entities.findIndex(e => e?.id === entity.id);
        if (index >= 0) {
            this._entities[index] = entity;
        } else {
            this._entities.push(entity);
        }
        return entity;
    }

    deleteById(id) {
        this._entities = this._entities.filter(e => e?.id !== id);
    }

}