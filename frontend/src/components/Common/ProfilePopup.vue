<template>
  <div v-if="user != null" class="account-link relative cursor-pointer select-none">
    <div class="flex flex-row gap-2 items-center">
      <img :src="user.avatarUrl" class="h-[25px] w-[25px] rounded-full">
      <div class="account-item">{{ displayName(true) }}</div>
    </div>
    <div class="account-popup-container pt-1">
      <div class="account-popup shadow">
        <div class="size-14 bold account-popup-info cursor-default">
          Hi, {{ displayName(false) }} 👋
        </div>
        <router-link class="flex flex-row plain items-center gap-2 account-link account-popup-item"
                     :to="{name:'profile'}">
          <font-awesome-icon icon="user" class="icon"></font-awesome-icon>
          <span>Profiel</span>
        </router-link>
        <div class="flex flex-row plain items-center gap-2 account-link logout-link account-popup-item"
             @click="confirmationLogOut">
          <font-awesome-icon icon="right-from-bracket" class="icon"></font-awesome-icon>
          <span>Log uit</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfilePopup",

  computed: {
    user() {
      let item = localStorage.getItem("user");
      console.log(item);
      return JSON.parse(item);
    },
  },

  methods: {
    displayName(lastName = false) {
      // user is a client
      if ('name' in this.user) {
        return this.participant.user.name;
      }

      // user is a specialist/admin
      let lastNameParts = this.user.lastName.split(" ");
      return this.user.firstName + (lastName ? " " + lastNameParts[lastNameParts.length - 1].charAt(0) + "." : "");
    },
    confirmationLogOut() {
      if (this.user == null) {
        return;
      }
      localStorage.setItem("user", null);
      localStorage.setItem("id", null);
      localStorage.setItem("role", null);
      location.reload();
    }
  }
}
</script>

<style scoped>

.account-link {
  font-size: 14px;
  font-family: Nunito, ui-sans-serif, sans-serif;
  font-weight: 500;
}

.account-popup-container {
  visibility: hidden;
  position: absolute;
  top: 100%;
  right: 0;
}

.account-link:hover .account-popup-container {
  visibility: visible;
}


.account-popup {
  background-color: #fff;
  color: #000;
  min-width: 200px;
  border-radius: 8px;
  border: 1px solid rgba(6, 6, 7, .08);
  font-size: 14px;
}

.account-popup-info {
  padding: 12px 15px 12px;
  font-weight: 700;
}

.account-popup svg {
  height: 16px;
  width: auto;
  fill: #ECECECFF;
}

.account-link {
  margin: 0;
  padding: 6px 8px;
  border-radius: 6px;
}

.account-link:hover {
  background-color: var(--neutral-100)
}

.account-popup-item {
  border-radius: 0;
}

.account-popup-item svg {
  width: 20px;
  fill: var(--neutral-900);
  color: var(--neutral-900)
}

.account-popup-item:last-child {
  border-radius: 0 0 6px 6px;
}

.logout-link:hover, .logout-link:hover svg {
  color: var(--app_red-400);
  fill: var(--app_red-400);
}

.account-popup > *:not(:first-child) {
  border-top: 1px solid rgba(144, 144, 144, 0.31);
}

.account-popup a:hover, .account-popup button:hover {
  background-color: var(--neutral-50);
}
</style>