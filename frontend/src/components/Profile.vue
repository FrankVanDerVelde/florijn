

<template>
    <div class="flex justify-center">
    <div class="profile-container flex self-center w-4/5">
        <div class="side-bar w-1/6">
            <span>Profiel</span>
            <span>CV</span>
        </div>
        <!-- <FormInput :name="test">test</FormInput> -->
        <div class="form grow">

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

            <span class="available-hours">Beschikbare uren</span>

        </div>
    </div>
</div>
</template>
  
<style scoped>
.profile-container {}

.input-container {
    display: flex;
    margin-bottom: 1em;
}

.input-container.duo {

}

.input-container.duo>div:first-child {
    margin-right: 1em;
}

.adress {
    font-family: 'Roboto', sans-serif;
    font-weight: 600;
    font-size: 20px;
}

.available-hours {
    font-family: 'Roboto', sans-serif;
    font-weight: 400;
    font-size: 30px;
}
</style>

<script>
export default {
    name: " Profile",
    components: {
        FormInput
    }
}
import FormInput from "./Common/FormInput.vue";

</script>