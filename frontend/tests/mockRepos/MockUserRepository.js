export default class SkillsRepository {

    constructor() {
    }

    async fetchSkillGroups() {
        // return await this.#networkClient.executeRequest('/skills/groups');
    }

    async fetchUserSkills(userId) {
        // return await this.#networkClient.executeRequest(`/skills/user-skills/${userId}`);
    }

    async fetchExpertises() {
        // return await this.#networkClient.executeRequest('/skills/expertises');
    }

    async fetchUserExpertises(userId) {
        // return await this.#networkClient.executeRequest(`/skills/user-expertises/${userId}`);
    }

    async updateUserExpertises(userId, expertises) {
        // return await this.#networkClient.executeRequest(
        //     `/skills/update-user-expertise/${userId}`,
        //     HttpMethod.PUT,
        //     expertises,
        //     {
        //         'Content-Type': 'application/json'
        //     }
        // );
    }

    async updateUserSkillGroup(userId, skillGroup) {
        // return await this.#networkClient.executeRequest(
        //     `/skills/update-user-skill-group/${userId}`,
        //     HttpMethod.PUT,
        //     skillGroup,
        //     {
        //         'Content-Type': 'application/json'
        //     }
        // );
    }

}