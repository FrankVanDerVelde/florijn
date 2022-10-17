

<template>
    <div class="page-main-mw p-4">
        <div class="profile-container flex self-center w-4/5">
            <div class="side-bar w-1/5">
                <span>Profiel</span>
                <span>CV</span>
            </div>
            <!-- <FormInput :name="test">test</FormInput> -->
            <div class="form grow">

                <div class="text-[20px] font-bold mb-[10px]">Profiel</div>
                <div class="name-and-picture-container flex items-center">
                    <img class="rounded-full w-[60px] h-[60px] mr-4"
                        src="https://media.istockphoto.com/vectors/thumbnail-image-vector-graphic-vector-id1147544807?k=20&m=1147544807&s=612x612&w=0&h=pBhz1dkwsCMq37Udtp9sfxbjaMl27JUapoyYpQm0anc=">
                    <div>
                        <div class="text-[20px] font-bold">Naam placeholder</div>
                        <div class="text-[14px] text-primary-400 font-bold">Title placeholder</div>
                    </div>
                </div>

                <form>
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

                    <span class="font-bold text-[20px]">Beschikbare uren</span>
                    <div v-for="day in days">
                        <WorkHoursInput :day="day" class="mb-[8px]"></WorkHoursInput>
                    </div>

                    <div class="font-bold text-[20px]">Skills</div>
                    <div class="uppercase text-neutral-400 font-bold">Office front End</div>

                    <div class="grid grid-cols-2">
                        <div></div>
                        <div class="grid grid-cols-6 gap-2 text-center">
                            <div>NVT</div>
                            <div>1</div>
                            <div>2</div>
                            <div>3</div>
                            <div>4</div>
                            <div>5</div>
                        </div>
                    </div>
                    <div v-for="skill in skills">
                        <div class="grid grid-cols-2 bg-neutral-100 my-3 h-[38px] items-center">
                            <div class="ml-[6px] font-bold text-[14px]">{{ skill }}</div>
                            <div class="grid grid-cols-6 gap-2 justify-center bg-neutral-100 h-[25px]">

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


                </form>

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

.input-container.duo {}

.input-container.duo>div:first-child {
    margin-right: 1em;
}



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
    background-color: #eee;
    border: 2px solid black;
    border-radius: 50%;
}

/* hover background */
.radio-button:hover input~.checkmark {
    background-color: #ccc;
}

/* checked background */
.radio-button input:checked~.checkmark {
    background-color: white;
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
    top: 7px;
    left: 7px;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background: black;
}
</style>

<script>
export default {
    name: " Profile",
    components: {
        FormInput,
        WorkHoursInput
    },
    data() {
        return {
            days: ["Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo"],
            skills: ["Html", "Php", "javascript"]
        }
    }
}
import FormInput from "./Common/FormInput.vue";
import WorkHoursInput from "./Common/WorkHoursInput.vue";

</script>