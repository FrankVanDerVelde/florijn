<template>
  <div class="container main-container rounded-xl">
    <div class="flex  mt-4 p-2 w-full justify-center">
      <img v-bind:src="customer.logoSrc" alt="project logo" class="icon-container fa-border m-auto">
      <div class="flex flex-col justify-between container ml-2">
        <div class="flex flex-col mb-3">
          <div class="font-bold">{{ customer.title }}</div>
          <div class="text-neutral-500">{{ customer.description }}</div>
          <button id="detailsbutton" class="bg-neutral-100 border-[1px] flex items-center rounded-md h-[31px] text-neutral-900">Details
          </button>
        </div>
      </div>
    </div>

  </div>

</template>

<script>

export default {


  name: "CustomerListDetails",

  props: {
    customer: {
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

#detailsbutton:hover{
  background-color: #F35627;
  color: white;
}

</style>