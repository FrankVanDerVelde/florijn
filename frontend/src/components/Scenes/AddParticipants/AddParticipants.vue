<template>


  <div class="page-main-mw p-4 ">
    <project-header :project="project"/>

    <div class="flex flex-row fa-border">
      <div class=" w-1/5 ">

        <FilterParticipants v-for="(skillset, index) in skills" :index="index" :skillset="skillset" :key="skillset"/>

      </div>

      <div class="flex flex-row flex-wrap self-start justify-evenly ">

        <ParticipantCard v-for="participants in participants" :key="participants.id" :skill="skills"
                         :participant="participants"></ParticipantCard>

      </div>

    </div>
  </div>

</template>

<script>
import ParticipantCard from "./ParticipantCard.vue";
import FilterParticipants from "./FilterParticipants.vue";
import ProjectHeader from "../Project/ProjectHeader.vue";

export default {
  name: "AddParticipants",
  components: {ProjectHeader, FilterParticipants, ParticipantCard},
  data() {
    return {
      participants: [
        {
          id: 0,
          firstName: "Andrew",
          lastName: "Alfred",
          role: "Product Owner",
          avatar: "/src/assets/avatars/avatar1.avif",
          email: "andrewa@florijn.com",
          assignedSkills: [{2: 4}]
        }, {
          id: 1,
          firstName: "Withney",
          lastName: "Keulen",
          role: "Lead Developer",
          avatar: "/src/assets/avatars/avatar2.avif",
          email: "withneyk@florijn.com",
          assignedSkills: [{2: 4}]

        }, {
          id: 2,
          firstName: "Jan",
          lastName: "Timmermans",
          role: "Designer",
          avatar: "/src/assets/avatars/avatar3.avif",
          email: "jant@florijn.com",
          assignedSkills: [{2: 4}]

        }
      ],
      skills: [{
        name: "Microsoft Office Front-end",
        skill: [{
          id: 0,
          name: "Microsoft Access",
          rating: 3
        }, {
          id: 1,
          name: "Microsoft Access VBA",
          rating: 2
        }, {
          id: 2,
          name: "Microsoft Excel",
          rating: 1
        }, {
          id: 3,
          name: "Microsoft Access",
          rating: 1
        }, {
          id: 4,
          name: "Microsoft Access",
          rating: 4
        }
        ]
      }, {
        name: "Back-end",
        skill: [{
          id: 0,
          name: "MS SQL Server",
          Rating: 3
        }, {
          id: 1,
          name: "MS SQL-Server Stored Procedures",
          Rating: 2
        }, {
          id: 2,
          name: "MS Office Excel",
          Rating: 5
        }, {
          id:3,
          name: "Ms office Eccel pt 2",
          rating: 3
        },]
      }],
      project: {
        bannerSrc: "/src/assets/ing-banner.jpg",
        logoSrc: "/src/assets/ING-Bankieren-icoon.webp",
        title: "ING Banking Web Application",
        description: "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
        participants: [
          {
            id: 0,
            firstName: "Andrew",
            lastName: "Alfred",
            role: "Product Owner",
            avatar: "/src/assets/avatars/avatar1.avif",
            email: "andrewa@florijn.com"
          }, {
            id: 1,
            firstName: "Withney",
            lastName: "Keulen",
            role: "Lead Developer",
            avatar: "/src/assets/avatars/avatar2.avif",
            email: "withneyk@florijn.com"
          }, {
            id: 2,
            firstName: "Jan",
            lastName: "Timmermans",
            role: "Designer",
            avatar: "/src/assets/avatars/avatar3.avif",
            email: "jant@florijn.com"
          }
        ]
      }
    }
  }
}


</script>

<style scoped>

</style>