<template>
    <div class="modal-container flex fixed w-full h-full top-0 left-0 z-50 justify-center items-center">
        <div class="modal flex flex-col relative bg-neutral-0">
            <div class="modal-contents flex flex-col w-full overflow-hidden">
                <div class="modal-header flex justify-center items-center flex-col mb-0 w-full">
                    <font-awesome-icon icon="circle-xmark"
                                       class="absolute top-2 right-2 text-neutral-400 cursor-pointer"
                                       size="2xl"
                                       @click="$emit('close')"/>

                    <h2>Registratie Informatie</h2>
                    <div class="subheader">{{ registry.participant.user.firstName }}'s uren registratie van {{ relativeDate }}</div>
                    <div class="mt-2 flex flex-col items-center">
                        <participant :participant="registry.participant" :small="true"/>
                    </div>
                </div>
                <div class="modal-body flex justify-start items-start flex-col overflow-auto mt-[15px]">
                    <div class="modal-body-content w-full">
                        <!-- smaller screens -->
                        <div class="grid grid-cols-1 grid-flow-row auto-rows-auto sm:grid-cols-2 gap-2 sm:hidden">
                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2">
                                    <div class="header">Start tijd</div>
                                    <div>{{ dateService.formatDate(registry.from) }}</div>
                                </div>
                            </div>
                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2 ">
                                    <div class="header">Eind tijd</div>
                                    <div>{{ dateService.formatDate(registry.to) }}</div>
                                </div>
                            </div>

                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2">
                                    <div class="header">Uurloon</div>
                                    <div>&euro;{{ registry.participant.hourlyRate }}</div>
                                </div>
                            </div>
                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2">
                                    <div class="header">{{ costTitle }}</div>
                                    <div>&euro;{{ costs }} ({{ formattedTimeSpent }})</div>
                                </div>
                            </div>

                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2">
                                    <div class="header">Beschrijving</div>
                                    <div>{{ registry.description }}</div>
                                </div>
                            </div>
                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2">
                                    <div class="header">Status</div>
                                    <HourRegistryStatus :status="registry.status"/>
                                </div>
                            </div>
                        </div>

                        <!-- Change structure for bigger screens -->
                        <div class="hidden sm:grid grid-cols-1 grid-flow-row auto-rows-auto justify-items-center sm:grid-cols-2 gap-2">
                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2">
                                    <div class="header">Start tijd</div>
                                    <div>{{ dateService.formatDate(registry.from) }}</div>
                                </div>
                                <div class="flex flex-col mb-2">
                                    <div class="header">Uurloon</div>
                                    <div>&euro;{{ registry.participant.hourlyRate }}</div>
                                </div>
                                <div class="flex flex-col mb-2">
                                    <div class="header">Beschrijving</div>
                                    <div>{{ registry.description }}</div>
                                </div>
                            </div>

                            <div class="col-span-1 flex flex-col">
                                <div class="flex flex-col mb-2">
                                    <div class="header">Eind tijd</div>
                                    <div>{{ dateService.formatDate(registry.to) }}</div>
                                </div>
                                <div class="flex flex-col mb-2">
                                    <div class="header">{{ costTitle }}</div>
                                    <div>&euro;{{ costs }} ({{ formattedTimeSpent }})</div>
                                </div>
                                <div class="flex flex-col mb-2">
                                    <div class="header">Status</div>
                                    <div>
                                        <HourRegistryStatus :status="registry.status"/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div v-if="registry.status == null && user?.role !== 'SPECIALIST'" id="review-btns" class="flex gap-2 mt-6">
                            <button class="reject" @click="$emit('changeStatus', false)">Afwijzen</button>
                            <button class="accept" @click="$emit('changeStatus', true)">Goedkeuren</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import HourRegistryStatus from "./HourRegistryStatus.vue";
import Participant from "./Participant.vue";

export default {
    name: "HoursInfoPopup",
    components: {HourRegistryStatus, Participant},
    inject: ['dateService'],
    emits: ['close', 'changeStatus'],

    computed: {
        costTitle() {
            return this.userId >= 2 ? "Kosten" : "Verdiensten";
        },
        userId() {
            return Number.parseInt(localStorage.getItem('userId'));
        },
        formattedTimeSpent() {
            return this.dateService.formatTimeSpent(this.dateService.calculateTimeSpent(this.registry.from, this.registry.to));
        },
        costs() {
            const time = this.dateService.calculateTimeSpent(this.registry.from, this.registry.to)[2];
            return Math.round(time * this.registry.participant.hourlyRate * 100) / 100;
        },
        relativeDate() {
            return this.dateService.formatDateRelatively(new Date(this.registry.to));
        },
        user() {
            return JSON.parse(localStorage.getItem('user'));
        }
    },

    props: {
        registry: {
            type: Object,
            required: true,
        }
    }
}
</script>

<style scoped>
.modal-container {
    background: rgba(0, 0, 0, 0.5);
}

.modal {
    width: 600px;
    visibility: visible;
    max-height: 85%;
    border-radius: 8px;
    margin: 0 auto;
}

h2 {
    text-align: center;
    margin-top: 60px;
    font-weight: 600;
    font-size: 30px;
    color: #000;
    margin-bottom: .5rem;
    line-height: 1.2;
    font-family: Inter, sans-serif;
}

.subheader {
    font-size: 18px;
    color: #1F2933;
    text-align: center;
    margin-bottom: 0;
    padding: 0 30px;
    font-weight: 500;
    line-height: 1.2;
    font-family: Inter, sans-serif;
}

.modal-body-content {
    padding: 30px 100px 50px;
}

.header {
    font-size: 16px;
    color: #000;
    font-family: Inter, sans-serif;
    font-weight: 600;
}

button {
    width: 100%;
    padding: 10px 15px;
    font-size: 16px;
    font-weight: 500;
    font-family: Inter, sans-serif;
    border-radius: 8px;
    transition: all .3s;
}

button.reject {
    border: 2px solid var(--app_red-200);
    background: var(--app_red-100);
    color: var(--app_red-500);
}

button.reject:hover {
    border-color: var(--app_red-300);
    background: var(--app_red-300);
    color: #fff;
}

button.accept {
    background-color: var(--app_green-400);
    color: #fff;
}

@media screen and (max-width: 640px) {
    .modal-body-content {
        padding: 30px 60px 50px;
    }
}

@media screen and (max-width: 600px) {
    .modal {
        width: 90%;
        margin: 0 auto;
    }
}
</style>