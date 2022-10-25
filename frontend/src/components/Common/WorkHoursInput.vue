<template>
    <div class="root flex justify-start items-center" :class="{inactive: !elementActive}">
        <label for="day" @click="toggleActive">
            <div
                class="bg-primary-200 text-primary-600 rounded-full font-bold inline-block w-[40px] h-[40px] flex justify-center mr-[14px]">
                <div class="mt-[8px]">
                    {{ day }}
                </div>
            </div>
        </label>

        <div class="relative inline-block">
            <input type="time" id="begin-time"
                class="border-solid border-[1px] border-neutral-300 text-neutral-600 rounded w-[6em] h-[25px]" 
                placeholder="12:00" :disabled="!elementActive">
        </div>
        <div>
            <font-awesome-icon icon="fa-solid fa-window-minimize" class="mx-[10px] align-super" />
        </div>
        <div class="relative inline-block">
            <input type="time" id="end-time"
                class="border-solid border-[1px] border-neutral-300 text-neutral-600 rounded w-[6em] h-[25px]"
                placeholder="12:00" :disabled="!elementActive">
        </div>
    </div>
</template>
  
<script>
export default {
    name: "TextField",
    props: {
        day: String,
    },
    methods: {
        toggleActive: function () {
            this.elementActive = !this.elementActive;
        }
    },
    data() {
        return {
            elementActive: true,
        }
    }

}


</script>
  
<style scoped>
input[type="time"]::-webkit-calendar-picker-indicator {
    display: block;
    top: 0;
    left: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    opacity: 0;
    overflow: hidden;
    
    
}

input[type="time"] {
    text-align: center;
}

input[type="time"]::-webkit-datetime-edit-ampm-field {
   display: none;
 }

input[type="time"]::-webkit-clear-button {
   -webkit-appearance: none;
   -moz-appearance: none;
   -o-appearance: none;
   -ms-appearance:none;
   appearance: none;
   margin: -10px; 
 }

.root.inactive>label>div {
    background-color: lightgray;
    color: rgb(44, 44, 44);
}

.root.inactive > div input {
    background-color: lightgray;
    
}
</style>