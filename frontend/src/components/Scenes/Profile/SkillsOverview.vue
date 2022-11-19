

<template>
    <div class="font-bold text-[32px] mb-3">Skills</div>

    <div v-for="group in this.skillGroups" class="shadow-lg rounded-lg mb-8">
            <SkillGroup :group="group"/>
    </div>


    <div>
        <div>Expertise gebied</div>
        <div class="text-primary-500 bg-primary-100 rounded-full px-[11px] py-[4px] font-bold inline-block mt-2">
            Financieel Administratief</div>
    </div>
</template>
  
<style scoped>
    * {
        font-family: 'Roboto';
    }
</style>

<script>
import SkillGroup from "./SkillsGroup.vue";

export default {
    name: "Profile",
    components: { SkillGroup },
    inject: ['skillFetchService'],
    async created() {
        let skillGroups = await this.skillFetchService.fetchJson(`/groups`);
        const userSkills = await this.skillFetchService.fetchJson(`/user-skills/1`);

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
    },
    data() {
        return {
            skillGroups: [],
        }
    }
}

</script>