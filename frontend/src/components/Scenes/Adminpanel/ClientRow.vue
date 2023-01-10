<template>
  <div class="container bg-neutral-50  rounded-xl">
    <div class="flex mt-4 p-2 w-full justify-center">
      <Asset :src="client.avatarUrl" alt="project logo" class="image-container icon-container"/>
      <div class="flex flex-col justify-between container ml-2">
        <div class="flex flex-col mb-3">
          <div class="container flex justify-between m-1">
            <div class="relative bottom-0 ">
              <div class="font-bold">{{ client.name }}</div>
              <div class="font-semibold text-neutral-500">{{ client.email }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Asset from "../../Common/Asset.vue";

export default {
  name: "ClientRow",
  components: {Asset},
  props: {
    client: {
      type: Object,
      required: true
    }
  },
  created() {
    console.log(this.client);
  }
}
</script>

<style scoped>
.image-container {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  border-radius: 6px;
}

.icon-container > img {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  object-fit: cover;
  -o-object-fit: cover;
  border-radius: 18px;
  border: 4px solid #fff;
  background-color: #fff;
}

#detailsbutton:hover {
  background-color: #F35627;
  color: white;
}
</style>