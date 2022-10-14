<template>
  <div class="flex items-center" :class="status.toLowerCase()">
    <div class="circle"></div>
    <div>{{ this.statusText }}</div>
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

  props: {
    status: {
      type: String,
      required: true,
    },
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