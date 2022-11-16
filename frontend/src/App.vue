<template>
  <div>
    <NavBar></NavBar>
    <router-view></router-view>
  </div>
</template>

<script>
import {HourRegistrationRepository} from "./Networking/HourRegistrationRepository.js";
import {DateService} from "./Services/DateService.js";
import {UserAdaptor} from "./Services/user-adaptor.js";

export default {
  name: "App",
  provide() {
    return {
      hourRegistrationRepository: new HourRegistrationRepository(),
      dateService: new DateService(),
      userService: new UserAdaptor()
    }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');
</style>