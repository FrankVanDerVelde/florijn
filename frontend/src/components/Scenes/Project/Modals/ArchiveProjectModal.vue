<template>
  <Modal @close="cancel" @click:submit="submit" :title="title" :is-open="open" :buttons="buttons">
    <p class="text-sm text-gray-600">
      {{ shortDescription }}
    </p>
    <p class="text-sm text-gray-600 mt-2">
      {{ bigDescription }}
    </p>

    <ProjectConfirmationInput v-model="projectTitle" :needed="project.title"/>
  </Modal>
</template>

<script>
import Modal from "../../../Common/Modal.vue";
import ProjectConfirmationInput from "../ProjectConfirmationInput.vue";

export default {
  name: "ArchiveProjectModal",
  components: {ProjectConfirmationInput, Modal},
  inject: ['projectFetchService'],

  data() {
    return {
      open: false,
      busy: false,
      projectTitle: ''
    }
  },

  computed: {
    title() {
      return this.archive ? 'Project archiveren' : 'Project dearchiveren'
    },
    shortDescription() {
      return `Markeer dit project als ${this.archive ? 'gearchiveerd' : 'ongearchiveerd'}.`;
    },
    bigDescription() {
      return this.archive ? 'Gearchiveerde projecten zullen niet worden weergegeven in project lijsten en bepaalde rechten zullen worden gelimiteerd.' :
          'Niet-gearchiveerde projecten zullen actief worden weergegeven in project lijsten, en kunnen actief uren worden geregistreerd.'
    },

    disabled() {
      return this.projectTitle.toLowerCase() !== this.project.title.toLowerCase();
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
          title: this.archive ? 'Archiveren' : 'Dearchiveren'
        },
      }
    }
  },

  props: {
    project: {
      type: Object,
      required: true
    },
    archive: {
      type: Boolean,
      default: true
    }
  },

  methods: {
    cancel() {
      this.open = false;
      this.projectTitle = "";
    },
    async submit() {
      if (this.disabled || this.busy) return;
      this.busy = true;

      this.originalClient = this.project.client;
      const res = await this.projectFetchService.fetchJsonPost(`/${this.project.id}/${this.archive ? 'archive' : 'unarchive'}`, {
            title: this.projectTitle
          }
      );

      if (res == null) {
        console.error(`Could not ${this.archive ? 'archive' : 'unarchive'} project.`);
        return;
      }

      this.$router.push({name: 'project-overview', params: {projectId: this.project.id}})
      this.busy = false;
    }
  }
}
</script>