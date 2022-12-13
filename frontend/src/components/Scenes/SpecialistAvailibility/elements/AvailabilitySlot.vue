<template>
  <div class="bg-neutral-0 rounded-[4px] border-[1px] border-neutral-100 hour-registration-row-shadow  border-neutral-100 w-full cursor-pointer hover:bg-neutral-100">
    <div class="py-[13px] px-[12px] flex justify-between">
      <div class="flex flex-col">
        <div class="flex flex-col justify-center items-center gap-2 text-neutral-800">
          <p class="font-semibold text-[14px]">{{ '10:00 - 14:00' }}</p>
          <p class="right-0 text-[13px]">{{ '4h 30m' }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AvailabilitySlot"
}
</script>

<style scoped>

</style>