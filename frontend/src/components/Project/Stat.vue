<template>
  <div class="stat flex flex-row items-center content-center">
    <div class="stat-dot" v-if="dot"></div>
    <div class="stat-icon" v-if="icon != null">
      <font-awesome-icon :icon="icon"/>
    </div>
    <div class="stat-info">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "Stat",

  props: {
    dot: {
      type: Boolean,
      default: true
    },
    icon: {
      default: undefined
    }
  }
}
</script>

<style scoped>
.stat {
  font-size: 12px;
  line-height: 16px;
}

.stat-dot {
  border-radius: 50%;
  width: 8px;
  height: 8px;
  margin-right: 8px;
  flex-shrink: 0;
  background-color: var(--neutral-300);
}

.stat-icon {
  font-size: 16px;
  line-height: 20px;
  margin-right: 8px;
  color: var(--neutral-600);
}

.stat-info {
  margin-right: 16px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: var(--neutral-400);
}

</style>