<template>
        <div v-for="item in sideBarItems">
        <router-link :to="item.href">
        <div class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex items-center rounded-md">
            <font-awesome-icon :icon="item.icon"></font-awesome-icon>
            <div>{{ item.name }}</div>
        </div>
    </router-link>
    </div>
   
</template>
  
<style scoped>
.router-link-active, .router-link-active div {
  color: var(--primary-500) !important;
  background-color: var(--neutral-0) !important;
  border: 1px solid var(--neutral-200);
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