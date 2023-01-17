import InMemoryEntitiesService from "./InMemoryEntitiesService";
import {Project} from "../../src/components/models/Project";

export default class InMemoryUserRepo extends InMemoryEntitiesService {

    constructor() {
        super();
    }

    static shared = new InMemoryUserRepo();

    fetchUsers(role) {
        return super.entities.filter(user => user.role === role);
    }

    // get entities() {
    //     return this._entities;
    // }

    // constructor(initialId = 10000, sampleCreator = null) {
    //     this._lastId = initialId;
    //     this._entities = [];
    //     if (sampleCreator != null) {
    //         for (let i = 0; i < 7; i++) {
    //             this.save(sampleCreator(0));
    //         }
    //     }

    // }

 

}