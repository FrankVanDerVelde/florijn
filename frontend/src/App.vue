<template>
  <div>
    <NavBar></NavBar>
    <div class="p-8 z-10">
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
    </div>
  </div>

</template>

<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://vuejs.org/api/sfc-script-setup.html#script-setup
import HelloWorld from './components/HelloWorld.vue'
</script>