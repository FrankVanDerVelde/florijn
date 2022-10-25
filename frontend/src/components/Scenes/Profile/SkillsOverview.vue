

<template>
    <div class="font-bold text-[32px] mb-3">Skills</div>

    <div v-for="skill in skills">
        <div class="border-[1px] border-neutral-100 bg-neutral-0 my-3 h-[38px] items-center rounded-lg">
            <div class="grid grid-cols-4 gap-2 h-full items-center justify-center col-span-2 text-center">
                <div class="font-bold capitalize text-[14px] text-left ml-[16px]">{{ skill.name}}</div>
                <div>{{ skill.date}}</div>
                <div>{{ skill.average}}</div>
                <div>
                    <!-- :to="this.iets" -->
                    <router-link to="/profile/skills/edit">
                        <div
                            class="bg-primary-500 text-neutral-0 border-neutral-200 border-[1px] flex items-center rounded-md h-[26px] mb-[10px] hover:bg-primary-100 capitalize font-bold text-[14px]">
                            <div>aanpassen</div>
                        </div>
                    </router-link>

                </div>
            </div>
        </div>
    </div>

    <div>
        <div>Expertise gebied</div>
        <div>Financieel Administratief</div>
    </div>
</template>
  
<style scoped>

</style>

<script>
export default {
    name: "Profile",
    data() {
        return {
            skills: [
                {
                    name: "Html",
                    date: "23/04/2022",
                    average: "75%"
                },
                {
                    name: "Php",
                    date: "23/04/2022",
                    average: "75%"
                },
                {
                    name: "javascript",
                    date: "23/04/2022",
                    average: "75%"
                },
                {
                    name: "react",
                    date: "23/04/2022",
                    average: "75%"
                },
                {
                    name: "vue",
                    date: "23/04/2022",
                    average: "75%"
                },
                {
                    name: "css",
                    date: "23/04/2022",
                    average: "75%"
                },
                {
                    name: "node",
                    date: "23/04/2022",
                    average: "75%"
                },
                {
                    name: "docker",
                    date: "23/04/2022",
                    average: "75%"
                }
            ]
        }
    }
}

</script>