import InMemoryEntitiesService from "./InMemoryEntitiesService";
import {Project} from "../../src/components/models/Project";
import {UserRole} from "../../src/components/models/UserRole";

export default class InMemoryProjectRepo extends InMemoryEntitiesService {

    #hoursRepo;
    #user;
    userRepo;

    constructor(hoursRepo = null, createSamples = true, user = null) {
        super(1, Project.createSample, createSamples ? 2 : 0);
        this.#hoursRepo = hoursRepo;
        this.#user = user;
    }

    static shared = new InMemoryProjectRepo();

    fetchProjectReports(projectId) {
        return [];
    }

    fetchProjectHourRegistrationsForUser(projectId) {
        return this.#hoursRepo.getForProject(projectId);
    }

    fetchProjectById(projectId) {
        let findById = this.findById(projectId);
        if (findById != null) {
            if (!this.#user || (this.#user.role === UserRole.client && findById.client.id != this.#user.id)) {
                throw new Error('You are not allowed to view this project');
            }
        }

        return findById;
    }

    /**
     * @param {FormData} formData
     */
    async createProject(formData) {
        let project = new Project(0, formData.get('title'), formData.get('description'), Number.parseInt(formData.get('client')));
        this.save(project);
        return project;
    }

    #confirmArchiveProject(projectId, body, archive) {
        let project = this.findById(projectId);
        if (!project) return null;

        if (body.title !== project.title) {
            throw new Error('Title does not match');
        }

        project.archived = archive;
        this.save(project);
        return project;
    }

    async archiveProject(projectId, body) {
        return this.#confirmArchiveProject(projectId, body, true);
    }

    async unarchiveProject(projectId, body) {
        return this.#confirmArchiveProject(projectId, body, false);
    }

    async transferProject(projectId, body) {
        let project = this.findById(projectId);
        if (!project) return null;

        if (body.title !== project.title) {
            throw new Error('Title does not match');
        }

        project.client = this.userRepo.findById(body.clientId);
        this.save(project);
        return project;
    }

    fetchProjectByUserId(id){
        return null;
    }
}