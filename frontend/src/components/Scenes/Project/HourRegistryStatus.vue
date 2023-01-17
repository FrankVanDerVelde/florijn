<template>
    <div class="flex items-center" :class="[statusTypeLowerCase]">
        <div class="circle"></div>
        <div class="status-value">{{ this.statusText }}</div>
    </div>
</template>

<script>
import {HourRegistrationStatus} from "../../models/HourRegistration/HourRegistrationStatus";

export default {
    name: "hourRegistryStatus",

    computed: {
        statusType() {
            return !this.status ? "AWAITING" : this.status;
        },
        statusTypeLowerCase() {
            return this.statusType.toLowerCase();
        },
        statusText() {
            switch (this.status) {
                case HourRegistrationStatus.rejected:
                    return "Afgewezen";
                case HourRegistrationStatus.accepted:
                    return "Goedgekeurd";
                default:
                    return "In afwachting";
            }
        }
    },

    props: {
        status: {
            required: true,
        }
    }
}
</script>

<style scoped>
.circle {
    border-radius: 50%;
    width: 14px;
    height: 14px;
    margin-right: 8px;
    flex-shrink: 0;
}

.awaiting .circle {
    background-color: var(--app_yellow-500);
}

.rejected .circle {
    background-color: var(--app_red-500);
}

.accepted .circle {
    background-color: var(--app_green-500);
}

.awaiting {
    color: var(--app_yellow-400)
}

.rejected {
    color: var(--app_red-400)
}

.accepted {
    color: var(--app_green-400)
}

</style>