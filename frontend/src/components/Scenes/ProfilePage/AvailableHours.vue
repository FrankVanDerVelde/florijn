

<template>
      <span class="font-bold text-[20px]">Beschikbare uren</span>
                    <div v-for="day in days">
                        <WorkHoursInput :day="day" class="mb-[8px]"></WorkHoursInput>
                    </div>
</template>
  
<style scoped>

</style>

<script>
export default {
    name: "Profile",
    data() {
        return {

        }
    }
}

</script>