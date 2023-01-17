import InMemoryEntitiesService from "./InMemoryEntitiesService";

export default class InMemorySkillsRepo extends InMemoryEntitiesService {

    constructor() {
        super();
    }

    fetchUserSkills(id){
        return [];
    }


}