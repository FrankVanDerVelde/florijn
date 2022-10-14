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

            <stats>
              <stat :dot="false" icon="users">{{ participantCount }} Deelnemers</stat>
            </stats>
          </section>

          <section class="pt-[24px]">
            <h2>Deelnemers</h2>

            <div class="flex flex-row flex-wrap gap-8">
              <participant v-for="participant in participants" :key="participant.id" :participant="participant"/>
            </div>

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

export default {
  name: "ProjectOverview",
  components: {Stat, Stats, Participant},

  computed: {
    participantCount() {
      return this.participants.length;
    },
  },

  data() {
    return {
      bannerSrc: "/src/assets/Barentszplein6B-voorkant.webp",
      logoSrc: "/src/assets/logo-small.png",
      title: "Een nieuwe Florijn project",
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
  margin-top: -28px;
  z-index: 1;
}

.icon-container > img {
  margin-left: 48px;
  width: 48px;
  height: 48px;
  object-fit: cover;
  -o-object-fit: cover;
  border-radius: 12px;
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
  margin-bottom: 8px;
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

@media screen and (min-width: 1024px) {
  .banner-container > img {
    height: 400px;
  }

  .icon-container {
    margin-top: -52px;
  }

  .icon-container > img {
    width: 88px;
    height: 88px;
    border-width: 8px;
    border-radius: 24px;
  }
}

</style>