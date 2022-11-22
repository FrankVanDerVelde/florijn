<template>
  <div :class="{'page-main-mw': !preview, 'p-4': !preview, 'mt-4': !preview}">
    <ProjectBanner :project="project" :preview="preview"/>

    <div class="flex flex-col items-center">
      <ProjectLogo :project="project"/>

      <div class="mt-2 sm:mt-4 w-full">
        <div class="md:pl-[48px] md:pr-[48px] w-full">
          <ProjectHeader :project="project" :edit-button="!preview && userId >= 2"/>

          <router-view v-if="!preview && project != null" :project="project"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectHeader from "../ProjectHeader.vue";
import ProjectBanner from "../ProjectBanner.vue";
import ProjectLogo from "../ProjectLogo.vue";

export default {
  name: "ProjectLayout",
  components: {ProjectLogo, ProjectBanner, ProjectHeader},
  inject: ['projectFetchService'],

  watch: {
    '$route.query.userId': async function () {
      localStorage.setItem('userId', this.$route.query.userId);
    }
  },

  computed: {
    userId() {
      return Number.parseInt(localStorage.getItem('userId'));
    }
  },

  data() {
    return {
      project: {}
    }
  },

  props: {
    projectId: {
      type: String,
      default: "-1"
    },
    projectInfo: {
      type: Object,
      default() {
        return null;
      }
    },
    preview: {
      type: Boolean,
      default: false
    }
  },

  async created() {
    localStorage.setItem('userId', this.$route.query.userId ?? 2);

    if (this.projectInfo == null && this.projectId >= 0) {
      this.project = await this.projectFetchService.fetchJson(`/${this.projectId}`);
    } else {
      this.project = this.projectInfo ?? {};
    }

    // when a non-existing project is requested, redirect to the /projects page.
    if (this.project == null) {
      this.$router.redirect({name: 'projects'});
    }
  },
}
</script>

<style>
h2, .header-2 {
  font-weight: 700;
  font-size: 22px;
  line-height: 34px;
  margin-bottom: 16px;
  color: var(--neutral-700)
}
</style>

<style scoped>


th {
  font-weight: 600;
  font-size: 18px;
  line-height: 26px;
  padding-bottom: 12px;
}
</style>