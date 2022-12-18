<template>
  <div>
    <NavBar></NavBar>
    <router-view></router-view>
  </div>
</template>

<script>
import {DateService} from "./Services/DateService.js";
import FetchService from "./Services/FetchService.js";
import {StoredTokenRepository} from "./Networking/Authentication/StoredTokenRepository.js";
import {AuthenticationRepository} from "./Networking/Authentication/AuthenticationRepository.js";
import {HourRegistrationRepository} from "./Networking/HourRegistration/HourRegistrationRepository.js";
import {AvailabilityRepository} from "./Networking/Availibility/AvailabilityRepository.js";
import Holidays from "date-holidays";
import CONFIG from '/config.js'

export default {
  name: "App",
  provide() {
    return {
      hourRegistrationRepository: new HourRegistrationRepository(),
      dateService: new DateService(),
      projectFetchService: new FetchService("projects"),
      skillFetchService: new FetchService("skills"),
      specialistFetchService: new FetchService("/specialists"),
      userFetchService: new FetchService("/users"),
      fetchService: new FetchService(""),
      memoryAvailabilityRepository: new AvailabilityRepository(),
      storedTokenRepository: new StoredTokenRepository(CONFIG.JWT_STORAGE_ITEM),
      authenticationRepository: new AuthenticationRepository(),
      holidays: new Holidays('NL')
    }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&display=swap');
</style>