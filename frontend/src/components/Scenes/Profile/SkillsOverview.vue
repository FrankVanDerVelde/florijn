

<template>
    <div class="font-bold text-[28px] mb-3 uppercase">Skills</div>

    <div v-for="group in this.skillGroups" class="shadow-lg rounded-lg mb-8">
        <SkillGroup :group="group" />
    </div>

    <div class="font-bold text-[28px] mb-3 uppercase mt-4">Expertise gebied</div>
    <div class="mb-8">
        <Expertise :expertises="expertises" :userExpertises="userExpertises" @toggleExpertise="updateUserExpertises">
        </Expertise>
    </div>


    <div>
        <!-- <div class="text-primary-500 bg-primary-100 rounded-full px-[11px] py-[4px] font-bold inline-block mt-2">
            Financieel Administratief</div> -->
    </div>
</template>

<script>
import Expertise from "./Expertise.vue";
import SkillGroup from "./SkillsGroup.vue";

export default {
    name: "Profile",
    components: { SkillGroup, Expertise },
    inject: ['skillFetchService'],
    methods: {
        updateUserExpertises(expertiseId) {
            const userExpertiseIds = this.userExpertises.map((expertise) => expertiseId);
            if (!userExpertiseIds.includes(expertiseId)) {
                this.userExpertises.push({id: expertiseId, userId: this.user.id});     
            } else {
                this.userExpertises = this.userExpertises.filter(userExpertise => userExpertise.id != expertiseId);
            }
        }
    },
    async created() {
        let skillGroups = await this.skillFetchService.fetchJson(`/groups`);
        const userSkills = await this.skillFetchService.fetchJson(`/user-skills/${this.user.id}`);

        const userSkillIds = userSkills.map(userSkill => userSkill.skill.id);

        skillGroups = skillGroups.map(group => {

            return (
                {
                    ...group,
                    skills: (
                        group.skills.map(skill => {
                            // Check if the skill is part of the users skillset
                            if (userSkillIds.includes(skill.id)) {
                                // Add user rating to the data
                                return { ...skill, rating: userSkills.find(userSkill => userSkill.skill.id == skill.id).rating }
                            }
                            // Set user rating to null
                            return ({ ...skill, rating: 0 })
                        })
                    )
                }
            )
        })

        this.skillGroups = skillGroups;

        this.expertises = await this.skillFetchService.fetchJson(`/expertises`);
        this.userExpertises = await this.skillFetchService.fetchJson(`/user-expertises/${this.user.id}`);
    },
    data() {
        return {
            skillGroups: [],
            user: JSON.parse(localStorage.getItem("user")),
            expertises: [],
            userExpertises: []
        }
    }
}

</script>