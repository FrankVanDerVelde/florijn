<template>
  <div class="container main-container rounded-xl">
    <div class="flex mt-4 p-2 w-full justify-center">
      <img v-bind:src="client.profilePictureURL" alt="project logo" class="icon-container fa-border m-auto">
      <div class="flex flex-col justify-between container ml-2">
        <div class="flex flex-col mb-3">
          <div class="container flex justify-between m-1">
            <div class="relative bottom-0 ">
              <div class="font-bold">{{ client.name }}</div>
              <div class="font-semibold text-neutral-500">{{ client.email }}</div>
            </div>
            <button id="detailsbutton"
                    class="bg-neutral-100 border-neutral-200 border-[1px] font-semibold rounded-md bold p-2 mr-2 h-[31px] flex items-center text-neutral-900">
              Details
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

export default {


  name: "CustomerListDetails",

  props: {
    client: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>

.main-container {
  background-color: #F5F7FA;
}

.icon-container {
  margin-left: auto;
  margin-right: auto;
  width: 68px;
  height: 68px;
  border-radius: 18px;
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