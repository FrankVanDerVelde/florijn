import InMemoryEntitiesService from "./InMemoryEntitiesService";
import {Project} from "../../src/components/models/Project";
import {UserRole} from "../../src/components/models/UserRole";
import SkillsGroup from "../../src/components/Scenes/Profile/SkillsGroup.vue";

export default class InMemorySkillsRepo extends InMemoryEntitiesService {



    constructor() {
        super();
    }

    static shared = new InMemorySkillsRepo();

    fetchSkills() {
        return super.entities;
    }


}