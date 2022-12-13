<template>
  <router-link v-if="!small" :to="this.data.link" class="flex items-center h-full">{{ this.data.name }}</router-link>

  <router-link v-else :to="this.data.link" class="flex items-center relative h-[46px]">
    <div class="small-name">{{ this.data.name }}</div>
    <div class="absolute top-0 right-0 h-full flex items-center justify-center">
      <font-awesome-icon icon="chevron-right"/>
    </div>
  </router-link>
</template>

<script>
export default {
  name: "NavItem",

  props: {
    data: {
      type: Object,
      required: true
    },
    small: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
a {
  color: var(--neutral-900);
  font-weight: 500;
  font-size: 16px;
  line-height: 24px;
  text-decoration: none;
}
.small-name {
  font-size: 18px;
  line-height: 24px;
  padding: 8px;
  color: var(--neutral-700);
  font-weight: 500;
}
.router-link-active, .router-link-active .small-name {
  color: var(--primary-400) !important;
}
</style>