import InMemoryEntitiesService from "./InMemoryEntitiesService";
import {HourRegistration} from "../../src/components/models/HourRegistration/HourRegistration";
import InMemoryUserRepo from "./InMemoryUserRepo";
import {HourRegistrationStatus} from "../../src/components/models/HourRegistration/HourRegistrationStatus";
import InMemoryProjectRepo from "./InMemoryProjectRepo.js";
import Specialist from "../../src/components/models/user/Specialist";

export default class InMemoryHourRegistrationRepo extends InMemoryEntitiesService {

    constructor() {
        super(1);
        this.#createSampleHours();
    }

    #createSampleHours() {
        for (let project of InMemoryProjectRepo.shared.entities) {
            for (let i = 1; i <= 5; i++) {
                const participant = {
                    user: new Specialist(1, "Gineke", "Groot", "", "hello@fresh.com"),
                    hourlyRate: 20,
                    role: 'Developer',
                };

                super.entities.push(new HourRegistration(
                    i,
                    project,
                    participant,
                    new Date(2021, 1, 1, 8, 0, 0),
                    new Date(2021, 1, 1, 10, 0, 0),
                    "You know nothing, Jon Snow",
                    null
                ));
            }
        }
    }

    getForProject(projectId) {
        return this.entities.filter(hr => hr.project.id === projectId);
    }

    updateStatusForHourRegistration(regId, status) {
        const reg = this.findById(regId);
        if (reg == null) return null;

        reg.status = status;
        this.save(reg);
        return reg;
    }

    async acceptHourRegistration(regId) {
        return this.updateStatusForHourRegistration(regId, HourRegistrationStatus.accepted);
    }

    async rejectHourRegistration(regId) {
        return this.updateStatusForHourRegistration(regId, HourRegistrationStatus.rejected);
    }
}