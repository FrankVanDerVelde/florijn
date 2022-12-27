<template>
  <div class="flex flex-row mt-4 w-full md:w-auto" v-if="!small">
    <Asset :src="participant.user.avatarUrl" alt="Avatar" class="w-[82px] h-[82px] rounded-full mr-4"/>
    <div class="flex flex-col justify-between relative pr-[34px] w-full md:w-auto">
      <div class="edit-btn hover:bg-neutral-50 hover:border-app_red-500 transition-all" v-if="edit" @click="$emit('selectedParticipant', {participant})">
        <font-awesome-icon icon="trash-can"/>
      </div>
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
    <Asset :src="participant.user.avatarUrl" alt="Avatar" class="w-[32px] h-[32px] rounded-full mr-2"/>
    <div class="flex flex-col justify-between">
      <div class="font-bold text-sm">{{ name }}</div>
      <div class="font-semibold text-neutral-500 text-sm">{{ participant.role }}</div>
    </div>

  </div>

</template>

<script>
import Asset from "../../Common/Asset.vue";

export default {
  name: "Participant",
  components: {Asset},
  emits: ["selectedParticipant"],

  created() {
    console.log(this.participant.user.avatarUrl)
  },

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
    }, edit: {
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
  right: 0px;
  color: var(--neutral-300);
  border: 1px solid var(--neutral-200);
  padding: 3px 6px;
  font-size: 14px;
  border-radius: 6px;
}

.edit-btn {
  position: absolute;
  top: 0;
  right: 32px;
  color: var(--app_red-500);
  border: 1px solid var(--neutral-200);
  padding: 3px 6px;
  font-size: 14px;
  border-radius: 6px;
  cursor: pointer;
}

</style>