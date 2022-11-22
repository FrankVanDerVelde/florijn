

<template>
    <div>
                    <div class="grid grid-cols-3 border-x-[1px] border-neutral-200">
                       <div class="uppercase text-neutral-400 font-bold text-[12px] col-span-1 "></div>
                        <div class="grid grid-cols-6 gap-2 text-center text-[14px] font-bold col-span-2 h-[38px] ">
                            <div class="flex flex-col justify-center"><div>NVT</div></div>
                            <div class="flex flex-col justify-center"><div>1</div></div>
                            <div class="flex flex-col justify-center"><div>2</div></div>
                            <div class="flex flex-col justify-center"><div>3</div></div>
                            <div class="flex flex-col justify-center"><div>4</div></div>
                            <div class="flex flex-col justify-center"><div>5</div></div>
                        </div>
                    </div>
                    <div v-for="skill in skills" class="last:border-b-[1px] border-neutral-200">
                        <div class="grid grid-cols-3 border-[1px] border-b-0 border-neutral-200 h-[38px] items-center">
                            <div class="font-bold capitalize text-[14px] ml-6 col-span-1">{{ skill.name }}</div>
                            <div class="grid grid-cols-6 gap-2 justify-center h-[25px] col-span-2">

                                <div v-for="index in 6" :key="index">
                                    <div class="flex justify-center">
                                    <label :for="`${skill.id}-${index - 1}`" class="radio-button" @click="() => { this.handleSkillUpdate(skill, index -1)}">
                                        <input :id="`${skill.name}-${index - 1}`" type="radio" :value="`${index - 1}`" :name="`${skill.name}-${index - 1}`" :checked="skill.rating == index - 1">
                                        <span class="checkmark"></span>
                                    </label>
                                    </div>
                                </div>                               

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
    top: 0px;
    left: 5px;
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
    emits: ['updateSkill'],
    props: {
    skills: {
      type: Object,
      required: true
    }
  },
  methods: {
    handleSkillUpdate(skill, newValue){
        // console.log(skill, newValue)
            this.$emit("updateSkill", skill.id, newValue)
        }
  }
    
}

</script>