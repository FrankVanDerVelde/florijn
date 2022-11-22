<template>
  <div class="page-main-mw p-4">
    <div class="banner-container grid">
      <img :src="project.client.bannerSrc" alt="banner">
    </div>

    <div class="flex flex-col items-center">
      <div class="icon-container grid w-full">
        <img :src="project.logoSrc" alt="project logo">
      </div>

      <div class="mt-2 sm:mt-4 w-full">
        <div class="md:pl-[48px] md:pr-[48px] w-full">
          <ProjectHeader :project="project" :edit-button="userId >= 2"/>

          <router-view :project="project"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectHeader from "../ProjectHeader.vue";

export default {
  name: "ProjectOverview",
  components: {ProjectHeader},
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

  props: {
    projectId: {
      type: String,
      required: true
    }
  },

  async created() {
    localStorage.setItem('userId', this.$route.query.userId ?? 2);
    this.project = await this.projectFetchService.fetchJson(`/${this.projectId}`);

    // when a non-existing project is requested, redirect to the /projects page.
    if (this.project == null) {
      this.$router.push({name: 'projects'});
      return;
    }

    console.log(this.project.client)
  },

  data() {
    return {
      project: {}
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
.banner-container {
  margin-top: 40px;
  width: 100%;
}

.banner-container > img {
  background-color: #d9d9d9;
  grid-column: span 12;
  width: 100%;
  height: 218px;
  object-fit: cover;
  -o-object-fit: cover;
  border-radius: 16px;
}

.icon-container {
  margin-top: -32px;
  z-index: 1;
}

.icon-container > img {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  object-fit: cover;
  -o-object-fit: cover;
  border-radius: 18px;
  border: 4px solid #fff;
  background-color: #fff;
}

th {
  font-weight: 600;
  font-size: 18px;
  line-height: 26px;
  padding-bottom: 12px;
}

@media screen and (min-width: 1024px) {
  .banner-container > img {
    height: 400px;
  }

  .icon-container {
    margin-top: -52px;
  }

  .icon-container > img {
    margin-left: 48px;
    margin-right: unset;
    width: 88px;
    height: 88px;
    border-width: 8px;
    border-radius: 24px;
  }
}
</style>