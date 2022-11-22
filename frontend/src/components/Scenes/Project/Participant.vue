<template>
  <div class="flex flex-row mt-4 w-full md:w-auto" v-if="!small">
    <img :src="participant.user.avatarUrl" alt="Avatar" class="w-[82px] h-[82px] rounded-full mr-4">
    <div class="flex flex-col justify-between relative pr-[34px] w-full md:w-auto">
      <a class="email-btn hover:bg-neutral-50 hover:border-neutral-100 transition-all" :href="'mailto:' + participant.user.email">
        <font-awesome-icon icon="envelope"/>
      </a>
      <div class="flex flex-col">
        <div class="font-bold">{{ name }}</div>
        <div class="font-semibold text-neutral-500">{{ participant.role }}</div>
      </div>
      <div class="text-neutral-400">
        {{ participant.user.email }}
      </div>
    </div>
  </div>
  <div class="flex flex-row items-center w-max" v-else>
    <img :src="participant.user.avatarUrl" alt="Avatar" class="w-[32px] h-[32px] rounded-full mr-2">
    <div class="flex flex-col justify-between">
      <div class="font-bold text-sm">{{ name }}</div>
      <div class="font-semibold text-neutral-500 text-sm">{{ participant.role }}</div>
    </div>
  </div>

</template>

<script>
export default {
  name: "Participant",

  computed: {
    name() {
      // user is a client
      if ('name' in this.participant.user) {
        return this.participant.user.name;
      }

      // user is a specialist/admin
      let lastNameParts = this.participant.user.lastName.split(" ");
      return this.participant.user.firstName + " " + lastNameParts[lastNameParts.length - 1].charAt(0) + ".";
    },
  },

  props: {
    participant: {
      type: Object,
      required: true
    },
    small: {
      type: Boolean,
      default: false
    },
    client: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
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