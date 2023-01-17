import InMemoryEntitiesService from "./InMemoryEntitiesService";

export default class InMemoryAvailabilityRepo extends InMemoryEntitiesService {

    constructor() {
        super();
    }

    fetchAvailabilityForUserInWeek(id, week, year){
        return [];
    }

}
