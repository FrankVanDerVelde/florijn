<template>
    <div v-for="item in sideBarItems">
        <router-link :to="item.href">
            <div class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex items-center rounded-md h-[38px] w-[180px] mb-[10px] hover:bg-primary-600 capitalize font-bold text-[14px]">
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