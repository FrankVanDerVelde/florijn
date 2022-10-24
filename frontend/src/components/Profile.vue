

<template>
    <div class="page-main-mw p-4">
        <div class="profile-container flex self-center w-4/5">
            <div class="side-bar w-1/5">
                <SideBarNav :sideBarItems="sideBarLinks"></SideBarNav>
            </div>
            <router-view></router-view>
            <!-- <FormInput :name="test">test</FormInput> -->
            <div class="form grow">

          
                <form>
                    
                  

                  


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
    name: "Profile",
    components: {
    FormInput,
    WorkHoursInput,
    SideBarNav
},
    data() {
        return {
            days: ["Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo"],
            skills: ["Html", "Php", "javascript"],
            sideBarLinks: [{
                icon: "fa-solid fa-user",
                name: "profiel",
                href: "/profile/personal-info"
            },
            {
                icon: "fa-solid fa-file",
                name: "available hours",
                href: "/profile/available-hours"
            },
            {
                icon: "fa-solid fa-file",
                name: "skills",
                href: "/profile/skills"
            },
            {
                icon: "fa-solid fa-file",
                name: "cv",
                href: "/profile/cv"
            },
            {
                icon: "fa-solid fa-file",
                name: "resume",
                href: "/profile/resume"
            }]
        }
    }
}
import FormInput from "./Common/FormInput.vue";
import WorkHoursInput from "./Common/WorkHoursInput.vue";
import SideBarNav from "./Common/SideBarNav.vue";

</script>