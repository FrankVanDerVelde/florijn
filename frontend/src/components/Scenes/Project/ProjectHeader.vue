<template>
  <section class="border-bottom">
    <h1>{{ project.title }}</h1>
    <p class="description">{{ project.description }}</p>

    <div class="flex items-center justify-between mb-[24px]">
      <stats class="!pb-0">
        <stat :dot="false" icon="users">{{ participantCount }} Deelnemers</stat>
      </stats>
      <button
          v-if="editButton"
          class="bg-primary-400 rounded-md bold p-2 h-[32px] flex items-center text-neutral-0">Wijzig informatie</button>
    </div>
  </section>
</template>

<script>
import Stats from "./Stats.vue";
import Stat from "./Stat.vue";
export default {
  name: "ProjectHeader",
  components: {Stat, Stats},

  computed: {
    participantCount() {
      return this.project.participants.length;
    },
  },

  props: {
    project: {
      type: Object,
      required: true
    },
    editButton: {
      type: Boolean,
      default: true,
    }
  }
}
</script>

<style scoped>

h1 {
  font-weight: 900;
  font-size: 34px;
  margin-bottom: 8px;
  line-height: 40px;
  color: var(--primary-500);
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
</style>