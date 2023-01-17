<template>
    <div class="border-[1px] border-neutral-200 bg-neutral-0 h-[58px] items-center rounded-t-lg">
        <div class="items-center flex justify-between h-full  col-span-2 text-center">
            <div class="font-bold capitalize text-[14px] text-left ml-[16px] text-neutral-600">Expertise</div>
            <div></div>
            <div></div>
            <div class="flex justify-end mr-3">
                <div
                id="formButton"
                    class="bg-primary-500 text-neutral-0 active:bg-white:text-primary-500 flex justify-center items-center rounded-md h-[32px] w-[96px] hover:bg-primary-600 capitalize font-bold text-[14px] text-center cursor-pointer"
                    @click="handleFormButton">
                    <div>{{ this.active ? "save" : "aanpassen" }}</div>
                </div>
            </div>

        </div>
    </div>

    <transition name="collapse" @enter="enter" @after-enter="afterEnter" @leave="leave">
        <div class="border-x-[1px] border-neutral-200 grid grid-cols-3 p-3" v-if="this.active">
            <div v-for="expertise in expertises" class="min-h-[44px] flex justify-start items-center">
                <div class="radio-button-container flex column justify-start items-center">

                    <label class="text-neutral-400 font-semibold text-[12px] w-[25px] h-[25px] mr-[10px]" v-bind:for="`input-${expertise.id}`">
                        <input type="checkbox" v-bind:id="`input-${expertise.id}`" @change="$emit('toggleExpertise', expertise.id)"
                               :checked="(this.userExpertiseIds.includes(expertise.id))">
                        <span class="checkmark cursor-pointer"></span>
                    </label>
                    <div class="text-neutral-900 break-word cursor-default">{{ expertise.name }}</div>
                </div>
            </div>

        </div>
    </transition>
    <div class="h-[5px] bg-primary-500 rounded-b-lg"></div>
</template>

<style scoped>
.collapse-enter-active,
.collapse-leave-active {
    transition: height .5s ease-in-out;
    overflow: hidden;
}
</style>

<script>

export default {
    name: "Profile",
    inject: ['skillsRepository'],
    emits: ["toggleExpertise"],
    props: {
        expertises: {
            type: Array,
            required: true
        },
        userExpertises: {
            type: Array,
            default: [],
            required: true
        }
    },
    computed: {
        userExpertiseIds() {
            return this.userExpertises.map(expertise => expertise.id)
        }
    },
    data() {
        return {
            active: false,
            user: JSON.parse(localStorage.getItem("user")),
        }
    },
    methods: {
        enter(element) {
            element.style.height = 'auto';
            const height = getComputedStyle(element).height;
            element.style.height = 0;

            setTimeout(() => {
                element.style.height = height;
            })

        },
        afterEnter(element) {
            element.style.height = 'auto';
        },
        leave(element) {
            element.style.height = getComputedStyle(element).height;
            getComputedStyle(element);
            setTimeout(() => {
                element.style.height = 0;
            })
        },
        handleFormButton() {
            if (this.active) {
                this.skillsRepository.updateUserExpertises(this.user.id, this.userExpertises);
            }
            this.active = !this.active;
        }
    }
}
</script>

<style scoped>

.radio-button-container {
    font-size: 14px;
    color: var(--neutral-50);
    position: relative;
    cursor: pointer;
}

/* Hide the browser's default checkbox */
.radio-button-container input {
    position: absolute;
    opacity: 0;
}

/* Create a custom checkbox */
.checkmark {
    position: relative;
    display: inline-block;
    height: 25px;
    width: 25px;
    background-color: var(--neutral-50);
    border: 1px solid var(--neutral-300);;
    border-radius: 25%;
}

/* On mouse-over, add a grey background color */
.radio-button-container:hover input ~ .checkmark {
    background-color: var(--primary-300);
}

/* When the checkbox is checked, add a background */
.radio-button-container input:checked ~ .checkmark {
    background-color: var(--primary-500);
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
    content: "";
    position: absolute;
    display: none;
}

/* Show the checkmark when checked */
.radio-button-container input:checked ~ .checkmark:after {
    display: block;
}

/* Style the checkmark/indicator */
.radio-button-container .checkmark:after {
    left: 9px;
    top: 5px;
    width: 5px;
    height: 10px;
    border: solid var(--neutral-50);
    border-width: 0 3px 3px 0;
    -webkit-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    transform: rotate(45deg);
}

</style>