<template>
    <div class="border-[1px] border-neutral-200 bg-neutral-0 h-[58px] items-center rounded-t-lg">
        <div class="grid grid-cols-4 gap-2 h-full items-center justify-center col-span-2 text-center">
            <div class="font-bold capitalize text-[14px] text-left ml-[16px]">{{ group.name }}</div>
            <div></div>
            <div></div>
            <div class="flex justify-end mr-3">
                <div class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex justify-center items-center rounded-md h-[32px] w-[96px] hover:bg-primary-600 capitalize font-bold text-[14px] text-center cursor-pointer"
                    @click="handleFormButton">
                    <div>{{ this.active ? "save" : "aanpassen"}}</div>
                </div>
            </div>
           
        </div>
    </div>

    <transition name="collapse" @enter="enter" @after-enter="afterEnter" @leave="leave">
        <SkillsForm :skills="group.skills" v-if="this.active" @updateSkill="handleSkillUpdate" />
    </transition>
    <div class="h-[5px] bg-primary-500 rounded-b-lg"></div>
</template>

<style scoped>
.collapse-enter-active,
.collapse-leave-active {
    transition: height .5s ease-in-out;
    overflow: hidden;
}
</style>

<script>
import SkillsForm from "./SkillsForm.vue";

export default {
    name: "Profile",
    components: { SkillsForm },
    inject: ['skillFetchService'],
    props: {
        group: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            active: false,
        }
    },
    methods: {
        enter(element) {
            element.style.height = 'auto';
            const height = getComputedStyle(element).height;
            element.style.height = 0;

            setTimeout(() => {
                element.style.height = height;
            })

        },
        afterEnter(element) {
            element.style.height = 'auto';
        },
        leave(element) {
            element.style.height = getComputedStyle(element).height;
            getComputedStyle(element);
            setTimeout(() => {
                element.style.height = 0;
            })
        },
        handleFormButton() {
            this.active = !this.active;
            if (!this.active) {
                console.log(this.group)
                this.skillFetchService.fetchJsonMethod(`/update-user-skill-group/5`, "PUT", this.group);
            }
        },
        handleSkillUpdate(skillId, newValue) {
            this.group.skills.find(skill => skill.id === skillId).rating = newValue;
        }

    }
}
</script>

<!-- {
    "id": 0,
    "name": "Front-End",
    "description": "This is the group for Front-End",
    "skills": [
        {
            "id": 0,
            "name": "HTML",
            "description": "Your ability to use HTML",
            "rating": 5
        },
        {
            "id": 1,
            "name": "CSS",
            "description": "Your ability to use CSS",
            "rating": 5
        },
        {
            "id": 2,
            "name": "Javascript",
            "description": "Your ability to use Javascript",
            "rating": 5
        }
    ]
} -->