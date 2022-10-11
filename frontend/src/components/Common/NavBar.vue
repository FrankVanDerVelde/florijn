<template>
  <div class="sticky top-0 z-50 h-[73px] bg-neutral-0 border-b border-b-neutral-200 shadow shadow">
    <div class="w-full flex justify-between items-center h-full mx-8">
      <img class="h-[42px]" :src="logo" alt="">
    </div>

    <div class="nav-items-container">
      <div>
        <router-link to="/home">Home</router-link>
      </div>
      <div>
        <router-link to="/profile">Profile</router-link>
      </div>
      <div>
        <router-link to="/project-overview">Project Overview</router-link>
      </div>
    </div>

  </div>
</template>

<script setup>
import logo from '../../assets/florijn_logo.png';
</script>

<style scoped>
/* Temp style for nav */
.nav-items-container {
  position: absolute;
  left: 211px;
  top: 20px;
  display: flex;
  width: 28em;
  justify-content: space-evenly;
}
</style>