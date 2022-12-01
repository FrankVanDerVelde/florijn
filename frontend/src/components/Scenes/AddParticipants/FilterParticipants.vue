<template>
  <div>
    <div class="font-semibold mb-2 mt-4">{{ skillset.name }}</div>

    <div v-if="expand">
      <div class="text-neutral-400 ml-4 mb-1" v-for="selectedSkill in skillset.skills" :key="selectedSkill">
        <label class="mark">
          <input type="checkbox" id="checkbox" v-model="selectedSkill.checked"
                 @click="$emit('selectedSkill', {'selectedSkill' : selectedSkill, 'checked' : selectedSkill.checked})">
          <span class="checkmark"></span>
        </label>
        <div class="pl-7">{{ selectedSkill.name }}</div>
      </div>
    </div>

    <div v-else>
      <div class="text-neutral-400 ml-4 mb-1" v-for="selectedSkill in skillset.skills.slice(0,3)" :key="selectedSkill">
        <label class="mark">
          <input type="checkbox" id="checkbox" v-model="selectedSkill.checked"
                 @click="$emit('selectedSkill', {'selectedSkill' : selectedSkill, 'checked' : selectedSkill.checked})">
          <span class="checkmark"></span>
        </label>
        <div class="pl-7">{{ selectedSkill.name }}</div>
      </div>
    </div>
    <div class="text-primary-500 font-bold pl-10 cursor-pointer" @click="expand = !expand">
      <div v-if="!expand">Uitbreiden</div>
      <div v-else>Inklappen</div>
    </div>

  </div>
</template>

<script>


export default {
  name: "FilterParticipants",
  emits: ['selectedSkill'],

  components: {},
  props: {
    skillset: {
      type: [Array, Object],
      required: true,
    }
  },

  data() {
    return {
      expand: false,

    }
  }
}


</script>

<style scoped>

.mark {
  display: block;
  position: relative;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.mark input {
  position: absolute;
  cursor: pointer;
  opacity: 0;
}

.checkmark {
  position: absolute;
  top: 0;
  bottom: 0;
  height: 18px;
  width: 18px;
  background-color: var(--neutral-50);
  border: 1px solid black;;
  border-radius: 15%;
}

.mark:hover input ~ .checkmark {
  background-color: #ccc;
}

.mark input:checked ~ .checkmark {
  background-color: var(--primary-300);
}

.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

.mark input:checked ~ .checkmark:after {
  display: block;
}

.mark .checkmark:after {
  left: 6px;
  top: 2px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  transform: rotate(45deg);
}


</style>
