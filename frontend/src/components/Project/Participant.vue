<template>
  <div class="flex flex-row mt-4 w-full md:w-auto">
    <img :src="participant.avatar" alt="Avatar" class="avatar">
    <div class="flex flex-col justify-between relative pr-[34px] w-full md:w-auto">
      <a class="email-btn hover:bg-neutral-50 hover:border-neutral-100 transition-all" :href="'mailto:' + participant.email">
        <font-awesome-icon icon="envelope"/>
      </a>
      <div class="flex flex-col">
        <div class="font-bold">{{ name }}</div>
        <div class="font-semibold text-neutral-500">{{ participant.role }}</div>
      </div>
      <div class="text-neutral-400">
        {{ participant.email }}
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: "Participant",

  computed: {
    name() {
      return this.participant.firstName + " " + this.participant.lastName.charAt(0) + ".";
    }
  },

  props: {
    participant: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
.avatar {
  width: 82px;
  height: 82px;
  aspect-ratio: 1/1;
  border-radius: 50%;
  margin-right: 16px;
}

.email-btn {
  position: absolute;
  top: 0;
  right: 0;
  color: var(--neutral-300);
  border: 1px solid var(--neutral-200);
  padding: 3px 6px;
  font-size: 14px;
  border-radius: 6px;
}
</style>