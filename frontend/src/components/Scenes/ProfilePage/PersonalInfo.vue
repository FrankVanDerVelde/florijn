

<template>

<div class="text-[20px] font-bold mb-[10px]">Profiel</div>
                <div class="name-and-picture-container flex items-center">
                    <img class="rounded-full w-[60px] h-[60px] mr-4"
                        src="https://media.istockphoto.com/vectors/thumbnail-image-vector-graphic-vector-id1147544807?k=20&m=1147544807&s=612x612&w=0&h=pBhz1dkwsCMq37Udtp9sfxbjaMl27JUapoyYpQm0anc=">
                    <div>
                        <div class="text-[20px] font-bold">Naam placeholder</div>
                        <div class="text-[14px] text-primary-400 font-bold">Title placeholder</div>
                    </div>
                </div>

  
  <div class="font-bold text-[20px]">Persoonlijke Informatie</div>

                    <div class="input-container duo">
                        <FormInput name="voornaam"></FormInput>
                        <FormInput name="achternaam"></FormInput>
                    </div>

                    <span class="adress">Woongegevens</span>
                    <div class="input-container">
                        <FormInput name="Email" type="email"></FormInput>
                    </div>

                    <div class="input-container duo">
                        <FormInput name="woonplaats"></FormInput>
                        <FormInput name="postcode"></FormInput>
                    </div>

                    <div class="input-container duo">
                        <FormInput name="huisnummer"></FormInput>
                        <FormInput name="toevoeging"></FormInput>
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