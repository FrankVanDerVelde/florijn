<template>
  <div class="page-main-mw p-4">
    <div class="banner-container grid">
      <img :src="project.bannerSrc" alt="banner">
    </div>

    <div class="flex flex-col items-center">
      <div class="icon-container grid w-full">
        <img :src="project.logoSrc" alt="project logo">
      </div>

      <div class="mt-2 sm:mt-4 w-full">
        <div class="md:pl-[48px] md:pr-[48px] w-full">
          <ProjectHeader :project="project"/>
          <ProjectParticipantList :edit-button="true" :participants="project.participants" :client="project.client"/>

          <section class="pt-[48px]">
            <h2>Uren</h2>
            <div class="grid grid-cols-12 gap-4">
              <SummaryBlock label="Totaal gemaakte uren" :value="totalHours"/>
              <SummaryBlock label="Gemaakte uren deze maand" :value="hoursThisMonth"/>
              <SummaryBlock label="Gemaakte uren deze week" :value="hoursThisWeek"/>
              <SummaryBlock label="Ontwikkelkosten" :value="totalCosts"/>
            </div>

            <div class="overflow-x-auto mb-6">
              <table class="w-full mt-4">
                <thead>
                <tr class="text-left">
                  <th>Deelnemer</th>
                  <th>Aantal uren</th>
                  <th>Kosten</th>
                  <th>Datum</th>
                  <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <hours-row v-for="registry in hourRegistry" :key="registry.id" :registry="registry"/>
                </tbody>
              </table>
            </div>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SummaryBlock from "./SummaryBlock.vue";
import HoursRow from "./HoursRow.vue";
import ProjectHeader from "./ProjectHeader.vue";
import ProjectParticipantList from "./ProjectParticipantList.vue";

export default {
  name: "ProjectOverview",
  components: {ProjectParticipantList, ProjectHeader, HoursRow, SummaryBlock},
  inject: ['projectFetchService'],

  computed: {
    totalHours() {
      return 24.5;
    },
    hoursThisMonth() {
      return 16.75;
    },
    hoursThisWeek() {
      return 8.25;
    },
    totalCosts() {
      return "€" + 2058.80;
    }
  },

  props: {
    projectId: {
      type: Number,
      required: true
    }
  },

  async created() {
    this.project = await this.projectFetchService.fetchJson(`/${this.projectId}`);

    // when a non-existing project is requested, redirect to the /projects page.
    if (this.project == null) {
      this.$router.push({name: 'projects'});
      return;
    }

    this.project.bannerSrc = "/src/assets/ing-banner.jpg";
    this.project.logoSrc = "/src/assets/ING-Bankieren-icoon.webp";
  },

  methods: {},

  data() {
    return {
      project: {},
      //
      // project: {
      //   bannerSrc: "/src/assets/ing-banner.jpg",
      //   logoSrc: "/src/assets/ING-Bankieren-icoon.webp",
      //   title: "ING Banking Web Application",
      //   description: "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
      //   participants: [
      //     {
      //       id: 0,
      //       firstName: "Andrew",
      //       lastName: "Alfred",
      //       role: "Product Owner",
      //       avatar: "/src/assets/avatars/avatar1.avif",
      //       email: "andrewa@florijn.com"
      //     }, {
      //       id: 1,
      //       firstName: "Withney",
      //       lastName: "Keulen",
      //       role: "Lead Developer",
      //       avatar: "/src/assets/avatars/avatar2.avif",
      //       email: "withneyk@florijn.com"
      //     }, {
      //       id: 2,
      //       firstName: "Jan",
      //       lastName: "Timmermans",
      //       role: "Designer",
      //       avatar: "/src/assets/avatars/avatar3.avif",
      //       email: "jant@florijn.com"
      //     }
      //   ]
      // },
      hourRegistry: [
        {
          id: 0,
          participant: {
            user: {
              id: 1,
              firstName: "Withney",
              lastName: "Keulen",
              avatarUrl: "/src/assets/avatars/avatar2.avif",
              email: "withneyk@florijn.com"
            },
            role: "Lead Developer",
          },
          startTime: "2022-10-14T12:00:00.000Z",
          endTime: "2022-10-14T14:45:00.000Z",
          hourlyRate: 60,
          status: 'AWAITING'
        },
        {
          id: 1,
          participant: {
            user: {
              id: 1,
              firstName: "Withney",
              lastName: "Keulen",
              avatarUrl: "/src/assets/avatars/avatar2.avif",
              email: "withneyk@florijn.com",
            },
            role: "Lead Developer"
          },
          startTime: "2022-10-13T09:00:00.000Z",
          endTime: "2022-10-13T11:30:00.000Z",
          hourlyRate: 60,
          status: 'APPROVED'
        },
        {
          id: 2,
          participant: {
            user: {
              id: 2,
              firstName: "Jan",
              lastName: "Timmermans",
              avatarUrl: "/src/assets/avatars/avatar3.avif",
              email: "jant@florijn.com"
            },
            role: "Designer",
          },
          startTime: "2022-10-11T14:00:00.000Z",
          endTime: "2022-10-11T18:20:00.000Z",
          hourlyRate: 40,
          status: 'APPROVED'
        },
        {
          id: 3,
          participant: {
            user: {
              id: 1,
              firstName: "Withney",
              lastName: "Keulen",
              avatarUrl: "/src/assets/avatars/avatar2.avif",
              email: "withneyk@florijn.com"
            },
            role: "Lead Developer",
          },
          startTime: "2021-03-08T15:30:00.000Z",
          endTime: "2021-03-08T19:20:00.000Z",
          hourlyRate: 60,
          status: 'DECLINED'
        }
      ]
    };
  },
}
</script>

<style>
h2 {
  font-weight: 700;
  font-size: 28px;
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
<style>
td, th {
  padding: 4px 8px;
  word-break: keep-all;
  white-space: nowrap;
  width: max-content;
  width: -moz-max-content;
}
</style>