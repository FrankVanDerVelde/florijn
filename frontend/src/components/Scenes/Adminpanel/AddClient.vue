<template>
  <div id="klanttoevoegen" class="text-[34px] font-bold">Klant toevoegen</div>
  <div class="page-main-mw pt-[5em] flex">
    <div class="profile-container flex self-center w-4/5">
      <div class="grid grid-cols-3 gap-4">
        <form>
          <label for="bedrijfsnaam" class="font-medium">Naam bedrijf</label><br>
          <input class="rounded border-[1px] border-neutral-200" type="text" placeholder="naam bedrijf" id="bedrijfsnaam" name="bedrijfsnaam"><br><br><br>

          <label class="font-medium">Gegevens contactpersoon</label><br>
          <input class="rounded border-[1px] border-neutral-200" type="text"  placeholder="voornaam" name="contactpersoon"><br>
          <input class="rounded border-[1px] border-neutral-200" type="text"  placeholder="achternaam" name="contactpersoon"><br>
          <input class="rounded border-[1px] border-neutral-200" type="text"  placeholder="telefoonnummer" name="contactpersoon"><br><br><br>

          <label class="font-medium" >Gegevens login</label><br>
          <input class="rounded border-[1px] border-neutral-200" type="text"  placeholder="gebruikersnaam" name="login"><br>
          <input class="rounded border-[1px] border-neutral-200" type="text"  placeholder="wachtwoord" name="login"><br><br><br>

          <button id="confirm" class="bg-primary-500 font-bold border-[1px] w-[121px] h-[38px] rounded-md text-neutral-0">Bevestigen</button>
          <button id="cancel" class="bg-neutral-900 font-bold border-[1px] w-[121px] h-[38px] rounded-md text-neutral-0">annuleren</button>
        </form>

      </div>
      <div>
        <svg width="300" height="303" viewBox="0 0 300 303" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect x="5" y="5" width="290" height="293" rx="17" fill="#FFD0B5" stroke="white" stroke-width="10"/>
        <path d="M132.983 130.561C128.626 130.561 125.094 134.049 125.094 138.352C125.094 142.655 128.626 146.144 132.983 146.144C137.34 146.144 140.872 142.655 140.872 138.352C140.872 134.049 137.338 130.561 132.983 130.561ZM181.483 116.276H118.372C112.71 116.276 108 120.927 108 126.665V178.608C108 184.346 112.709 188.996 118.371 188.996H181.482C187.291 188.996 192 184.346 192 178.608V126.665C192.001 120.927 187.433 116.276 181.483 116.276ZM184.113 177.52L161.629 147.345C161.218 146.598 160.282 146.144 159.279 146.144C158.274 146.144 157.337 146.593 156.779 147.341L139.259 170.732L133.164 163.249C132.598 162.554 131.695 162.144 130.736 162.144C129.776 162.144 128.874 162.554 128.307 163.249L115.895 178.476C115.895 178.471 115.895 178.481 115.895 178.476L115.889 126.665C115.889 125.233 117.068 124.068 118.518 124.068H181.629C183.079 124.068 184.259 125.233 184.259 126.665V177.52H184.113Z" fill="#F35627"/>
      </svg>
      </div>
      </div>
      <div class="grow">

      </div>
    </div>
</template>

<script>

import formInput from "../../Common/FormInput.vue";

export default {
  name: "AddClient",
  components: formInput,
}

</script>

<style scoped>

#klanttoevoegen{
  margin-left: 240px;
  padding-top: 50px;
}

.page-main-mw {
  margin-left: 240px;
}

input{
  padding-top: 4px;
  padding-bottom: 4px;
  margin: 2px;
  width: 400px;
}



</style>