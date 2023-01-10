<template>
  <div class="container main-container rounded-xl">
    <div>
      <div class="float-left star-space">
        <font-awesome-icon v-for="skill in skill.rating" :key="skill.rating" icon="star" class="star-color"/>
      </div>
      <div class="font-medium my-2">{{ skill.skill.name }}</div>
    </div>
  </div>

</template>

<script>
import Asset from "../../Common/Asset.vue";

export default {
  components: {Asset},
  name: "SkillListSummary",
  inject: ["fetchService"],

  props: {
    skill: {
      type: Object,
      required: true,
    }
  },

}
</script>

<style scoped>

.icon-container > img {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  object-fit: cover;
  -o-object-fit: cover;
  border-radius: 18px;
  border: 4px solid #fff;
}

.star-color {
  color: var(--primary-500);
}

.star-space{
  width: 110px;
}
</style>