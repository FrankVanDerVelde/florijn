import InMemoryEntitiesService from "./InMemoryEntitiesService";
import {Project} from "../../src/components/models/Project";

export default class InMemoryProjectRepo extends InMemoryEntitiesService {

    #hoursRepo;

    constructor(hoursRepo, createSamples = true) {
        super(1, Project.createSample, createSamples ? 2 : 0);
        this.#hoursRepo = hoursRepo;
    }

    static shared = new InMemoryProjectRepo();

    fetchProjectReports(projectId) {
        return [];
    }

    fetchProjectHourRegistrationsForUser(projectId) {
        return this.#hoursRepo.getForProject(projectId);
    }

    /**
     * @param {FormData} formData
     */
    async createProject(formData) {
        console.log('SAVING PROJECT');
        let project = new Project(0, formData.get('title'), formData.get('description'), Number.parseInt(formData.get('client')));
        this.save(project);
        return project;
    }

}