<template>
  <div class="flex flex-col md:flex-row gap-4">
    <SummaryBox v-for="summary in summaries" :title="summary.title" :value="summary.value" :key="summary.title"/>
  </div>

  <HomeProjectListBox :error-message="projectsBoxErrorMessage"
                      :label="projectsBoxLabel"
                      :project-count="projectCount"
                      :projects="projects"
  />

  <HomeBox v-if="hourRegistrations">
    <h2 class="text-xl font-semibold mb-2">Geregistreerde uren</h2>

    <div v-if="hourRegistrations.length === 0" class="flex gap-2 items-center text-app_red-500">
      <font-awesome-icon icon="fa-face-grin-squint" size="xl"></font-awesome-icon>
      <p>{{ hourRegistrationsBoxErrorMessage }}</p>
    </div>
    <div v-else class="overflow-x-auto">
      <table class="w-full mt-4">
        <thead>
        <tr class="text-left">
          <th>Project</th>
          <th>Aantal uren</th>
          <th>{{ hourRegistrationsCostLabel }}</th>
          <th>Datum</th>
          <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <HoursRow v-for="registry in hourRegistrations.slice(0, 10)"
                  :show-project="true"
                  :key="registry.id"
                  :registry="registry"/>
        </tbody>
      </table>
    </div>
  </HomeBox>

  <HomeBox class="w-full md:w-fit">
    <h2 class="text-xl font-semibold mb-2">Snelle acties</h2>
    <div class="flex flex-col md:flex-row gap-1 md:gap-5">
      <QuickAction v-for="action in quickActions"
                   :title="action.title"
                   :link="action.link"
                   :icon="action.icon"
                   :key="action.title"
      />
    </div>
  </HomeBox>

</template>

<script>
import SummaryBox from "../SummaryBox.vue";
import HomeProjectListBox from "../HomeProjectListBox.vue";
import HomeBox from "../HomeBox.vue";
import QuickAction from "../QuickAction.vue";
import HoursRow from "../../Project/HoursRow.vue";

export default {
  name: "HomeLayout",
  components: {HoursRow, QuickAction, HomeBox, HomeProjectListBox, SummaryBox},

  props: {
    summaries: {
      type: Array,
      required: true
    },
    projects: {
      type: Array,
      required: true
    },
    projectCount: {
      type: Number,
      required: true
    },
    quickActions: {
      type: Array,
      required: true,
    },
    hourRegistrations: {
      type: Array,
      required: false
    },
    projectsBoxLabel: {
      type: String,
      required: false,
      default: 'Actieve projecten'
    },
    projectsBoxErrorMessage: {
      type: String,
      required: false,
      default: 'Er zijn geen actieve projecten gevonden.'
    },
    hourRegistrationsBoxErrorMessage: {
      type: String,
      required: false,
      default: 'Je hebt nog geen uren geregistreerd.'
    },
    hourRegistrationsCostLabel: {
      type: String,
      required: false,
      default: 'Kosten'
    }
  }
}
</script>

<style scoped>
h2 {
  font-family: 'Poppins', sans-serif;
}
</style>