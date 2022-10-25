

<template>
    <div class="font-bold text-[32px] mb-3">Skills</div>
                
                    <div class="grid grid-cols-3">
                       <div class="uppercase text-neutral-400 font-bold text-[12px] col-span-1">Technische kennis</div>
                        <div class="grid grid-cols-6 gap-2 text-center text-[14px] font-bold col-span-2">
                            <div>NVT</div>
                            <div>1</div>
                            <div>2</div>
                            <div>3</div>
                            <div>4</div>
                            <div>5</div>
                        </div>
                    </div>
                    <div v-for="skill in skills">
                        <div class="grid grid-cols-3 border-[1px] border-neutral-100 bg-neutral-0 my-3 h-[38px] items-center rounded-lg">
                            <div class="font-bold capitalize text-[14px] ml-6 col-span-1">{{ skill }}</div>
                            <div class="grid grid-cols-6 gap-2 justify-center h-[25px] col-span-2">

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
.radio-button {
    display: block;
    position: relative;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    width: 25px;
}

.radio-button input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
}

.checkmark {
    position: absolute;
    top: 0;
    left: 0;
    height: 25px;
    width: 25px;
    background-color: var(--neutral-50);
    border: 1px solid var(--neutral-300);;
    border-radius: 50%;
}

/* hover background */
.radio-button:hover input~.checkmark {
    background-color: var(--primary-300);
}

/* checked background */
.radio-button input:checked~.checkmark {
    background-color: var(--primary-500);
}

/* create dot and hide */
.checkmark:after {
    content: "";
    position: absolute;
    display: none;
}

/* show dot when checked */
.radio-button input:checked~.checkmark:after {
    display: block;
}

/* checkmark dot */
.radio-button .checkmark:after {
    content: '\2713';
    top: 1px;
    left: 6px;
    width: 4px;
    height: 3px;
    border-radius: 50%;
    color: white;
    transform:scale(1,0.85); /* W3C */
    font-weight: bold;
}
</style>

<script>
export default {
    name: "Profile",
    data() {
        return {
            skills: ["Html", "Php", "javascript", "react", "vue", "css", "html", "node", "docker"],
        }
    }
}

</script>