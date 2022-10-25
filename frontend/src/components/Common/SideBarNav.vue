<template>
  <div v-for="item in sideBarItems">
    <router-link :to="item.href">
      <div class="text-primary-500 p-2 bg-neutral-0 border-neutral-200 border-[1px] flex items-center rounded-md h-[38px] w-[180px] mb-[10px] hover:bg-primary-100 capitalize font-bold text-[14px]">
        <div class="ml-[12px]">
          <font-awesome-icon :icon="item.icon"></font-awesome-icon>
        </div>
        <div class="ml-[12px]">{{ item.name }}</div>
      </div>
    </router-link>
  </div>

</template>

<style scoped>
.router-link-active > div {
  color: white !important;
  background-color: var(--primary-500) !important;
  border: none;
}
</style>

<script>
export default {
  name: "SideBarNav",
  components: {

  },
  data() {

  },
  props: {
    sideBarItems: Array
  }
}

</script>
