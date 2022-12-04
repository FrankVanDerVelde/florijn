<template>
  <Modal @close="cancel" @click:submit="submit" title="Project archiveren" :is-open="open" :buttons="buttons">
    <p class="text-sm text-gray-600">
      {{ shortDescription }}
    </p>
    <p class="text-sm text-gray-600 mt-2">
      {{ bigDescription }}
    </p>

    <div class="mt-3">
      <label class="block text-base leading-5 text-gray-600 font-bold mt-3">Bevestiging</label>
      <p class="text-sm text-gray-600 block mb-1">Typ de project titel in het invoerveld om te bevestigen dat je dit project wilt
        {{ archive ? "archiveren" : "dearchiveren" }}: "<kbd>{{ project.title }}</kbd>"</p>
      <input :placeholder="project.title" v-model="projectTitle"/>
    </div>
  </Modal>
</template>

<script>
import Modal from "../../../Common/Modal.vue";

export default {
  name: "ArchiveProjectModal",
  components: {Modal},
  inject: ['projectFetchService'],

  data() {
    return {
      open: false,
      originalClient: this.project.client,
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
      this.project.client = this.originalClient;
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

<style scoped>
input {
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
</style>