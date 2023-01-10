<template>
    <div class="page-main-mw">
        <div class="flex gap-3 md:flex-row flex-col-reverse md:flex-row mt-4">
            <div class="w-full p-4 pt-0 sm:pt-4 md:pt-4">
                <div class="hidden md:block">
                    <h1 class="text-3xl leading-tight mb-2 font-bold">{{ newProject? "Nieuw": "Wijzig" }} project</h1>
                    <hr>
                    <router-link :to="{ name: 'project-overview' }" class="muted error !mt-[-10px] block mb-2"
                        v-if="!newProject">
                        &#60; Terug naar project overzicht
                    </router-link>
                </div>
                <form class="pt-2" @submit.prevent="saveProject">
                    <label class="block text-base leading-5 text-gray-600 font-bold mt-3 w-full">
                        <div>Titel</div>
                        <input @input="e => updateTitle(e)" :value="title" type="text" placeholder="Project titel"
                            :class="{ 'error': 'title' in errors }">
                    </label>
                    <div class="muted error" v-if="'title' in errors">{{ errors.title }}</div>

                    <label class="block text-base leading-5 text-gray-600 font-bold mt-3 w-full">
                        <div>Omschrijving</div>
                        <input @input="e => updateDescription(e)" :value="description" type="text"
                            placeholder="Omschrijving" :class="{ 'error': 'description' in errors }">
                    </label>
                    <div class="muted error" v-if="'description' in errors">{{ errors.description }}</div>

                    <div v-if="newProject" class="block text-base leading-5 text-gray-600 font-bold mt-3 w-full">
                        <div>Klant</div>

                        <ClientSelect :clients="clients" v-model="project.client" />
                    </div>
                    <div class="muted error" v-if="'client' in errors">{{ errors.client }}</div>

                    <div class="banner-file-title">Banner file</div>
                    <div class="cursed-element">
                        
                        <label for="banner-upload-input" class="">
                            Choose file
                        </label>
                        <div class="file-name">{{( logoFile && logoFile.name) && logoFile.name }}</div>
                        <input @change="e => updateLogo(e)" type="file" name="banner-upload-input" class="hidden" :class="{ 'error': 'logo' in errors }" id="banner-upload-input" accept=".svg,.png,.webp,jpg,.jpeg">
                    </div>

                    <div class="muted error" v-if="'logo' in errors">{{ errors.logo }}</div>

                    <div class="muted">Het logo zal niet worden verwerkt voordat het project is {{
                        newProject?
                                            "aangemaakt": "opgeslagen"
                    }}.
                        Het huidige logo zal worden gebruikt als er geen logo wordt geüpload.
                    </div>

                    <div class="flex flex-col items-end mt-5">
                        <PrimaryButton type="submit" :title="newProject ? 'Maak project aan' : 'Update project'" />
                        <div class="muted error" v-if="'updating' in errors">{{ errors.updating }}</div>
                    </div>

                    <div v-if="!newProject && project != null" class="mt-6">
                        <h2 class="font-normal text-app_red-400 text-2xl mb">Danger Zone</h2>

                        <div class="mt-2 border border-app_red-200 rounded-md">
                            <ul>
                                <DangerZoneRow v-if="user.role === 'ADMIN'" @click="$refs.ownershipModal.open = true"
                                    title="Eigendom overdragen" button="Overdragen"
                                    description="Draag het eigendom van dit project over aan een andere klant." />

                                <DangerZoneRow v-if="!project.archived" @click="$refs.archiveModal.open = true"
                                    title="Archiveer dit project" button="Archiveer" description="Markeer dit project als gearchiveerd. Gearchiveerde projecten zullen niet actief in de project lijsten worden
                               weergegeven en bepaalde rechten zullen worden gelimiteerd." />
                                <DangerZoneRow v-else @click="$refs.unarchiveModal.open = true"
                                    title="Dearchiveer dit project" button="Dearchiveer"
                                    description="Dit project is momenteel gearchiveerd. Dit project zal niet actief in project lijsten worden weergegeven en
                               bepaalde rechten zijn gelimiteerd. Door het project te dearchiveren, hef je deze beperkingen op." />
                            </ul>
                        </div>

                        <TransferOwnershipModal ref="ownershipModal" :clients="clients" :project="project" />

                        <ArchiveProjectModal v-if="!project.archived" ref="archiveModal" :project="project" />
                        <ArchiveProjectModal v-else ref="unarchiveModal" :project="project" :archive="false" />

                    </div>
                </form>
            </div>
            <div class="w-full p-4">
                <div class="md:hidden mb-4">
                    <h1 class="text-3xl leading-tight mb-2 font-bold">{{ newProject? "Nieuw": "Wijzig" }} project</h1>
                    <hr>
                    <router-link :to="{ name: 'project-overview' }" class="muted error !mt-[-10px] block mb-2"
                        v-if="!newProject">
                        &#60; Terug naar project overzicht
                    </router-link>
                </div>
                <h2 class="font-semibold uppercase tracking-widest text-[20px] mb-2">Preview</h2>
                <ProjectLayout v-if="project != null" :project-info="project" :preview="true" />
            </div>
        </div>
    </div>
</template>

<script>
import ProjectLayout from "./ProjectLayout.vue";
import PrimaryButton from "../../../Common/PrimaryButton.vue";
import ClientSelect from "../ClientSelect.vue";
import DangerZoneRow from "../DangerZoneRow.vue";
import TransferOwnershipModal from "../Modals/TransferOwnershipModal.vue";
import ArchiveProjectModal from "../Modals/ArchiveProjectModal.vue";
import { UserRole } from "../../../models/UserRole.js";

export default {
    name: "CreateProject",
    components: {ArchiveProjectModal, TransferOwnershipModal, DangerZoneRow, ClientSelect, PrimaryButton, ProjectLayout},
    inject: ['projectRepository', 'userRepository', 'storedTokenRepository', 'stringService'],

    computed: {
        user() {
            return JSON.parse(localStorage.getItem('user'));
        }
    },

    async created() {
        // redirect the user to home if they do not have permission to view the page.
        if (this.user == null || (this.newProject && this.user.role !== UserRole.admin) ||
            (!this.newProject && this.user.role === UserRole.specialist)) {

            if (this.newProject) this.$router.push({ name: 'projects' });
            else this.$router.push({ name: 'project-overview' });
            return;
        }

        if (!this.newProject) {
            this.project = await this.projectRepository.fetchProjectById(this.projectId);

            if (this.project == null) {
                this.$router.push({ name: 'projects' });
                return;
            }
        } else {
            this.project = this.getSampleProject();
        }

        this.clients = await this.userRepository.fetchUsers(UserRole.client);
        if (!this.newProject) this.injectProjectInfo();

        // when a non-existing project is requested, redirect to the /projects page.
        if (this.project == null) {
            this.$router.push({ name: 'projects' });
        }
    },

    props: {
        projectId: {
            type: String,
            default: "-1"
        },
        newProject: {
            type: Boolean,
            default: false
        }
    },

    data() {
        return {
            project: null,
            title: "",
            description: "",
            logoFile: null,
            clients: [],
            errors: {},
        }
    },

    methods: {
        getSampleProject() {
            return {
                title: "Nieuw project",
                description: "Hier komt de beschrijving van het project.",
                participants: [],
                client: {},
                archived: false,
            }
        },
        updateTitle(event) {
            this.title = event.target.value.trim();
            if (this.title.length > 0) {
                delete this.errors.title;
            }

            this.project.title = this.title.length > 0 ? this.title : "Nieuw project";
        },
        updateDescription(event) {
            this.description = event.target.value.trim();
            if (this.description.length > 0) {
                delete this.errors.description;
            }

            this.project.description = this.description.length > 0 ? this.description : "Hier komt de beschrijving van het project.";
        },
        async updateLogo(event) {
            if (event.target.files.length === 0) {
                this.logoFile = null;
                delete this.project.logoSrc;
                return;
            }

            const fileExtension = event.target.files[0].name.split('.').pop();
            if (!['svg', 'png', 'webp', 'jpg', 'jpeg'].includes(fileExtension)) {
                this.errors.logo = "Alleen afbeeldingen met de extensies .svg, .png, .webp, .jpg en .jpeg zijn toegestaan.";
                return;
            }

            this.logoFile = event.target.files[0];
            this.project.logoSrc = `${await this.getBase64(event.target.files[0])}`;
        },
        injectProjectInfo() {
            this.title = this.project.title;
            this.description = this.project.description;

            if (this.project.client != null) {
                let filter = this.clients.filter(c => c.id === this.project.client?.id);
                if (filter.length > 0) this.project.client = filter[0];
            }

        },
        validate() {
            this.errors = {};

            if (this.title.trim().length === 0) {
                this.errors.title = "De titel mag niet leeg zijn.";
            }
            if (this.description.trim().length === 0) {
                this.errors.description = "De omschrijving mag niet leeg zijn.";
            }
            if (this.project.client?.id == null) {
                this.errors.client = "Er is geen klant geselecteerd.";
            }

            return Object.keys(this.errors).length === 0;
        },
        async saveProject() {
            if (!this.validate()) return;

            const formData = new FormData();
            formData.append('title', this.title);
            formData.append('description', this.description);
            formData.append('client', this.project.client.id);
            if (this.logoFile != null) formData.append('logoFile', this.logoFile);

            try {
                let response;
                if (this.newProject) {
                    response = await this.projectRepository.createProject(formData);
                } else {
                    response = await this.projectRepository.updateProject(this.projectId, formData);
                }

                // redirecting to the created project.
                this.$router.push(`/projects/${response.id}`);
            } catch (e) {
                let message = (await e.response.json()).message;
                if (message?.startsWith('Maximum upload size exceeded')) {
                    let split = message.split(" ");
                    const size = this.stringService.convertBytesToText(Math.round(Number.parseInt(split[split.length - 2])), true);

                    this.errors.logo = `Bestand is te groot. Maximum grootte is ${size}.`;
                    return;
                }

                this.errors.updating = `Er is een fout opgetreden tijdens het ${this.newProject ? "aanmaken" : "opslaan"} van het project.`;
            }
        },
        async getBase64(file) {
            // convert javascript file to base64 string
            return new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = () => resolve(reader.result);
                reader.onerror = error => reject(error);
            });
        }
    }
}
</script>

<style scoped>
hr {
    color: var(--neutral-100);
    height: 20px;
    /* border-bottom: 1px solid var(--neutral-100); */
}

.muted {
    font-size: 14px;
    line-height: 20px;
    margin-top: 8px;
    color: #5e6672;
}

.muted.error {
    margin-top: 4px;
    color: var(--app_red-400)
}

input,
select {
    width: 100%;
    border-color: rgb(209, 213, 219);
    --tw-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
    --tw-shadow-colored: 0 1px 2px 0 0 0 #0000;
    box-shadow: var(--tw-ring-offset-shadow, 0 0 #0000), 0 0 #0000, var(--tw-shadow);
    border-radius: 0.375rem;
    margin-top: .25rem;
    background: #fff;
    border-width: 1px;
    padding: 0.5rem 0.75rem;
    font-size: 1rem;
    line-height: 1.5rem;
    font-weight: 400;
}

input.error,
select.error {
    border-color: var(--app_red-300);
}

.cursed-element {
    width: 100%;
    border-color: rgb(209, 213, 219);
    --tw-shadow: 0 1px 2px 0 rgb(0 0 0 / 0.05);
    --tw-shadow-colored: 0 1px 2px 0 0 0 #0000;
    box-shadow: var(--tw-ring-offset-shadow, 0 0 #0000), 0 0 #0000, var(--tw-shadow);
    border-radius: 0.375rem;
    margin-top: 0.25rem;
    background: #fff;
    border-width: 1px;
    padding: 0.5rem 0.75rem;
    font-size: 1rem;
    line-height: 1.5rem;
    font-weight: 400;
    display: flex;
    align-items: center;
}

.cursed-element label {
    border: 1px solid gray;
    border-radius: 10px;
    padding: 4px 6px;
    cursor: pointer;
}

.banner-file-title {
color: #4b5563;
font-size: 16px;
font-weight: bold;
margin-top: 10px;
}

.file-name {
    margin-left: 10px;
}
</style>