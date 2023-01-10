<template>
    <div class="page-main-mw p-4 pt-[3em] flex flex-col">
        <h1 class="text-3xl mb-3 font-bold">Projecten</h1>

        <Searchbar placeholder="Zoek een project..."
                   v-model="searchQuery"
                   @submit="fetchProjects"/>

        <div v-if="initialLoadFinished && projects.length === 0 && archivedProjects.length === 0" class="text-app_red-400">Er konden geen projecten gevonden met de gegeven
            zoekopdracht.
        </div>

        <div class="flex flex-col gap-3">
            <h2 v-if="projects.length > 0" class="text-xl">Actieve projecten</h2>
            <project-list-details v-for="project in projects" :key="project.id" :project="project"/>

            <h2 v-if="archivedProjects.length > 0" class="text-xl mt-2">Gearchiveerde projecten</h2>
            <project-list-details v-for="project in archivedProjects" :key="project.id" :project="project"/>

        </div>
    </div>
</template>

<script>

import ProjectListDetails from ".././ProjectListDetails.vue";
import Searchbar from "../../../Common/Searchbar.vue";

export default {
    name: "ProjectList",
    components: {Searchbar, ProjectListDetails},
    inject: ['projectRepository'],
    created() {
        this.fetchProjects();
    },

    computed: {
        user() {
            return JSON.parse(localStorage.getItem("user"));
        }
    },

    methods: {
        async fetchProjects() {
            // making sure that they can't send another request before the previous one is finished
            if (this.loadingProjects) return;
            this.loadingProjects = true;

            const [active, archived] = await Promise.all([
                this.projectRepository.fetchProjects("UNARCHIVED", this.searchQuery),
                this.projectRepository.fetchProjects("ARCHIVED", this.searchQuery),
            ]);

            this.projects = active;
            this.archivedProjects = archived;

            this.loadingProjects = false;
            this.initialLoadFinished = true;
        }
    },

    data() {
        return {
            projects: [],
            archivedProjects: [],
            searchQuery: "",
            loadingProjects: false,
            initialLoadFinished: false,
            sideBarLinks: [{
                icon: 'fa-solid fa-user',
                name: 'projecten',
                href: '/projects',
            }],
        }
    }

}
</script>