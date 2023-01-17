<template>
    <Modal @close="cancel" @click:submit="submit" title="Eigendom overdragen" :is-open="open" :buttons="buttons">
        <p class="text-sm text-gray-600">
            Draag het eigendom van dit project over aan een andere klant.
        </p>
        <p v-if="true" class="text-sm mt-2 text-gray-600 italic">
            Let op: je bent eigenaar van dit project. Project eigendom overdragen naar een andere klant zal je rechten tot dit project intrekken.
        </p>

        <label class="block text-base leading-5 text-gray-600 font-bold mt-3">Nieuwe klant</label>
        <ClientSelect :clients="clients" v-model="project.client"/>

        <ProjectConfirmationInput v-model="projectTitle" :needed="project.title"/>
    </Modal>
</template>

<script>
import Modal from "../../../Common/Modal.vue";
import ClientSelect from "../ClientSelect.vue";
import ProjectConfirmationInput from "../ProjectConfirmationInput.vue";

export default {
    name: "TransferOwnershipModal",
    components: {ProjectConfirmationInput, ClientSelect, Modal},
    inject: ['projectRepository'],

    data() {
        return {
            open: false,
            originalClient: this.project.client,
            busy: false,
            projectTitle: ''
        }
    },

    computed: {
        disabled() {
            return this.projectTitle.toLowerCase() !== this.project?.title?.toLowerCase() || this.project?.client?.id === this.originalClient.id;
        },
        buttons() {
            return {
                cancel: {
                    type: 'secondary',
                    role: 'cancel',
                    title: 'Cancel'
                },
                submit: {
                    type: 'danger',
                    disabled: this.disabled,
                    title: 'Overdragen'
                },
            }
        }
    },

    props: {
        project: {
            type: Object,
            required: true
        },
        clients: {
            type: Array,
            required: true
        },
        user: {
            required: true,
        }
    },

    methods: {
        cancel() {
            this.open = false;
            this.projectTitle = "";
            this.project.client = this.originalClient;
        },
        async submit() {
            if (this.disabled || this.busy) return;
            this.busy = true;

            this.originalClient = this.project.client;
            let body = {
                clientId: this.project.client.id,
                title: this.projectTitle
            };
            const res = await this.projectRepository.transferProject(this.project.id, body);

            if (res == null) {
                console.error("Something went wrong whilst transferring ownership.")
                return;
            }

            if (this.user?.role === "ADMIN") {
                this.$router.push({name: 'project-overview', params: {projectId: this.project.id}})
            } else {
                this.$router.push({name: 'projects'});
            }

            this.busy = false;
        }
    }
}
</script>

<style scoped>

</style>