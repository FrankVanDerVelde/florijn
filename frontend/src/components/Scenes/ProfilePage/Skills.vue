

<template>
    <div class="font-bold text-[20px]">Skills</div>
                    <div class="uppercase text-neutral-400 font-bold">Office front End</div>

                    <div class="grid grid-cols-2">
                        <div></div>
                        <div class="grid grid-cols-6 gap-2 text-center">
                            <div>NVT</div>
                            <div>1</div>
                            <div>2</div>
                            <div>3</div>
                            <div>4</div>
                            <div>5</div>
                        </div>
                    </div>
                    <div v-for="skill in skills">
                        <div class="grid grid-cols-2 bg-neutral-100 my-3 h-[38px] items-center">
                            <div class="ml-[6px] font-bold text-[14px]">{{ skill }}</div>
                            <div class="grid grid-cols-6 gap-2 justify-center bg-neutral-100 h-[25px]">

                                <div class="flex justify-center">
                                    <label :for="`${skill}-nvt`" class="radio-button">
                                        <input :id="`${skill}-nvt`" type="radio" value="test" :name="`${skill}`">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>

                                <div class="flex justify-center">
                                    <label :for="`${skill}-1`" class="radio-button">
                                        <input :id="`${skill}-1`" type="radio" value="test" :name="`${skill}`">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>

                                <div class="flex justify-center">
                                    <label :for="`${skill}-2`" class="radio-button">
                                        <input :id="`${skill}-2`" type="radio" value="test" :name="`${skill}`">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>

                                <div class="flex justify-center">
                                    <label :for="`${skill}-3`" class="radio-button">
                                        <input :id="`${skill}-3`" type="radio" value="test" :name="`${skill}`">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>

                                <div class="flex justify-center">
                                    <label :for="`${skill}-4`" class="radio-button">
                                        <input :id="`${skill}-4`" type="radio" value="test" :name="`${skill}`">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>

                                <div class="flex justify-center">
                                    <label :for="`${skill}-5`" class="radio-button">
                                        <input :id="`${skill}-5`" type="radio" value="test" :name="`${skill}`">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>

                            </div>
                        </div>
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