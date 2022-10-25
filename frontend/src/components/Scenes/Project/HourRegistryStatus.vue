<template>
  <div v-if="!editing" class="flex items-center hover:cursor-pointer" :class="status.toLowerCase()" @click="edit">
    <div class="circle"></div>
    <div>{{ this.statusText }}</div>
  </div>

  <div v-else class="flex items-center gap-4">
    <button
        class="hover:bg-app_red-600 transition-all bg-app_red-400 text-neutral-0 rounded-sm p-2 h-[32px] w-[32px] flex items-center justify-center aspect-square"
        @click="submitChange(false)"
        title="Wijs deze registratie af.">
      <font-awesome-icon icon="xmark"/>
    </button>
    <button
        class="hover:bg-app_green-600 transition-all bg-app_green-400 text-neutral-0 rounded-sm p-2 h-[32px] w-[32px] flex items-center justify-center aspect-square"
        @click="submitChange(true)"
        title="Keur deze registratie goed.">
      <font-awesome-icon icon="check"/>
    </button>
  </div>
</template>

<script>
export default {
  name: "hourRegistryStatus",

  computed: {
    statusText() {
      switch (this.status) {
        case "AWAITING":
          return "In afwachting";
        case "DECLINED":
          return "Afgewezen";
        case "APPROVED":
          return "Goedgekeurd";
        default:
          return "Onbekend";
      }
    }
  },

  data() {
    return {
      editing: false
    }
  },

  props: {
    status: {
      type: String,
      required: true,
    },
  },

  methods: {
    edit() {
      this.editing = true;
    },
    submitChange(approved) {
      this.$emit('updateRegistryStatus', approved ? 'APPROVED' : 'DECLINED');
      this.editing = false;
    }
  }
}
</script>

<style scoped>
.circle {
  border-radius: 50%;
  width: 14px;
  height: 14px;
  margin-right: 8px;
  flex-shrink: 0;
}

.awaiting .circle {
  background-color: var(--app_yellow-500);
}

.declined .circle {
  background-color: var(--app_red-500);
}

.approved .circle {
  background-color: var(--app_green-500);
}

.awaiting {
  color: var(--app_yellow-400)
}

.declined {
  color: var(--app_red-400)
}

.approved {
  color: var(--app_green-400)
}

</style>