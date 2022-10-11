<template>
  <div>
    <NavBar></NavBar>
    <!-- <div class="p-8 z-10">
      <h1 class="text-xl font-bold mb-4">Components</h1>

      <div class="flex flex-col gap-8">
        <div class="flex gap-8 items-center">
          <p class="font-medium">PrimaryButton</p>
          <PrimaryButton :title="'test'" class="m-8"></PrimaryButton>
        </div>

        <div class="flex gap-8 items-center">
          <p class="font-medium">SecondaryButton</p>
          <SecondaryButton :title="'test'" class="m-8"></SecondaryButton>
        </div>

        <div class="flex gap-8 items-center">
          <p class="font-medium">TextField</p>
          <TextField :label="'test'" class="m-8"></TextField>
        </div>
      </div>
    </div> -->
  </div>
  <router-view></router-view>
</template>

<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://vuejs.org/api/sfc-script-setup.html#script-setup
import HelloWorld from './components/HelloWorld.vue'
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');
</style>