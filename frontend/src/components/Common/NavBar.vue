<template>
  <div class="sticky top-0 z-50 h-[73px] bg-neutral-0 border-b border-b-neutral-200 shadow shadow">
    <div class="page-main-mw flex flex-row items-center p-4 h-full">
      <img class="h-[42px] mr-6" :src="logo" alt="">

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
  </div>
</template>

<script setup>
import logo from '../../assets/florijn_logo.png';</script>

<style scoped>
/* Temp style for nav */
.nav-items-container {
  display: flex;
  column-gap: 20px;
  -webkit-column-gap: 20px;
}
</style>