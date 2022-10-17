<template>
  <div class="page-main-mw p-4">
    <div class="banner-container grid">
      <img :src="bannerSrc" alt="banner">
    </div>

    <div class="flex flex-col items-center">
      <div class="icon-container grid w-full">
        <img :src="logoSrc" alt="project logo">
      </div>

      <div class="mt-2 sm:mt-4 w-full">
        <div class="md:pl-[48px] md:pr-[48px] w-full">
          <section class="border-bottom">
            <h1>{{ title }}</h1>
            <p class="description">{{ description }}</p>

            <div class="flex items-center justify-between mb-[24px]">
              <stats class="!pb-0">
                <stat :dot="false" icon="users">{{ participantCount }} Deelnemers</stat>
              </stats>
              <button class="bg-primary-400 rounded-md bold p-2 h-[32px] flex items-center text-neutral-0">Wijzig informatie</button>
            </div>
          </section>

          <section class="pt-[24px]">
            <div class="flex items-center justify-between mb-2">
              <h2 class="!mb-0">Deelnemers</h2>
              <button class="bg-primary-400 rounded-md bold p-2 h-[32px] flex items-center text-neutral-0">Wijzigen</button>
            </div>

            <div class="flex flex-row flex-wrap gap-8">
              <participant v-for="participant in participants" :key="participant.id" :participant="participant"/>
            </div>
          </section>

          <section class="pt-[48px]">
            <h2>Uren</h2>
            <div class="grid grid-cols-12 gap-4">
              <SummaryBlock label="Totaal gemaakte uren" :value="totalHours"/>
              <SummaryBlock label="Gemaakte uren deze maand" :value="hoursThisMonth"/>
              <SummaryBlock label="Gemaakte uren deze week" :value="hoursThisWeek"/>
              <SummaryBlock label="Ontwikkelkosten" :value="totalCosts"/>
            </div>

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
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Stats from "./Project/Stats.vue";
import Stat from "./Project/Stat.vue";
import Participant from "./Project/Participant.vue";
import SummaryBlock from "./Project/SummaryBlock.vue";
import HoursRow from "./Project/hoursRow.vue";

export default {
  name: "ProjectOverview",
  components: {HoursRow, SummaryBlock, Stat, Stats, Participant},

  computed: {
    participantCount() {
      return this.participants.length;
    },
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

  data() {
    return {
      bannerSrc: "/src/assets/Barentszplein6B-voorkant.webp",
      logoSrc: "/src/assets/logo-small.png",
      title: "Een nieuw Florijn project",
      description: "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
      participants: [
        {
          id: 0,
          firstName: "Yuri",
          lastName: "Schrieken",
          role: "Product Owner",
          avatar: "/src/assets/avatars/avatar1.png",
          email: "yuris@florijn.com"
        }, {
          id: 1,
          firstName: "Sophie",
          lastName: "Keulen",
          role: "Lead Developer",
          avatar: "/src/assets/avatars/avatar2.png",
          email: "sophiek@florijn.com"
        }, {
          id: 2,
          firstName: "Jan",
          lastName: "Timmermans",
          role: "Designer",
          avatar: "/src/assets/avatars/avatar3.png",
          email: "jant@florijn.com"
        }
      ],
      hourRegistry: [
        {
          id: 0,
          participant: {
            id: 1,
            firstName: "Sophie",
            lastName: "Keulen",
            role: "Lead Developer",
            avatar: "/src/assets/avatars/avatar2.png",
            email: "sophiek@florijn.com"
          },
          startTime: "2022-10-14T12:00:00.000Z",
          endTime: "2022-10-14T14:45:00.000Z",
          hourlyRate: 60,
          status: 'AWAITING'
        },
        {
          id: 1,
          participant: {
            id: 1,
            firstName: "Sophie",
            lastName: "Keulen",
            role: "Lead Developer",
            avatar: "/src/assets/avatars/avatar2.png",
            email: "sophiek@florijn.com"
          },
          startTime: "2022-10-13T09:00:00.000Z",
          endTime: "2022-10-13T11:30:00.000Z",
          hourlyRate: 60,
          status: 'APPROVED'
        },
        {
          id: 2,
          participant: {
            id: 2,
            firstName: "Jan",
            lastName: "Timmermans",
            role: "Designer",
            avatar: "/src/assets/avatars/avatar3.png",
            email: "jant@florijn.com"
          },
          startTime: "2022-10-11T14:00:00.000Z",
          endTime: "2022-10-11T18:20:00.000Z",
          hourlyRate: 40,
          status: 'APPROVED'
        },
        {
          id: 3,
          participant: {
            id: 1,
            firstName: "Sophie",
            lastName: "Keulen",
            role: "Lead Developer",
            avatar: "/src/assets/avatars/avatar2.png",
            email: "sophiek@florijn.com"
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

h1 {
  font-weight: 900;
  font-size: 34px;
  margin-bottom: 8px;
  line-height: 40px;
  color: var(--primary-500);
}

h2 {
  font-weight: 700;
  font-size: 28px;
  line-height: 34px;
  margin-bottom: 16px;
  color: var(--neutral-700)
}

.description {
  color: var(--neutral-400);
  font-size: 20px;
  line-height: 28px;
  margin-bottom: 24px;
}

.border-bottom {
  border-bottom: 1px solid var(--neutral-100);
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