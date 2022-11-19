<template>
  <div v-if="!editing" class="flex items-center" :class="[statusTypeLowerCase, canEdit ? 'hover:cursor-pointer' : '']" @click="edit">
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
    statusType() {
      return this.status == null ? "AWAITING" : this.status;
    },
    statusTypeLowerCase() {
      return this.statusType.toLowerCase();
    },
    statusText() {
      switch (this.statusType) {
        case "REJECTED":
          return "Afgewezen";
        case "ACCEPTED":
          return "Goedgekeurd";
        default:
          return "In afwachting";
      }
    },
    canEdit() {
      return this.status == null;
    }
  },

  data() {
    return {
      editing: false
    }
  },

  props: {
    status: {
      type: [String, null],
      required: true,
    },
  },

  methods: {
    edit() {
      if (this.canEdit) {
        this.editing = true;
      }
    },
    submitChange(approved) {
      this.$emit('updateRegistryStatus', approved);
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

.rejected .circle {
  background-color: var(--app_red-500);
}

.accepted .circle {
  background-color: var(--app_green-500);
}

.awaiting {
  color: var(--app_yellow-400)
}

.rejected {
  color: var(--app_red-400)
}

.accepted {
  color: var(--app_green-400)
}

</style>