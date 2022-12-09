<template>
    <span class="font-bold text-[20px]">Beschikbare uren</span>
    <div v-for="day in days">
        <WorkHoursInput :day="day" class="mb-[8px]"></WorkHoursInput>
    </div>

    <div class="grid grid-cols-2 gap-4 row-1">
        <SaveButton></SaveButton>
    </div>
</template>
  
<style scoped>

</style>

<script>
export default {
    name: "Profile",
    components: {
        WorkHoursInput,
        SaveButton
    },
    data() {
        return {
            days: ["Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo"],
        }
    }
}

import WorkHoursInput from "../../Common/WorkHoursInput.vue";
import SaveButton from "../../Common/SaveButton.vue";

</script>