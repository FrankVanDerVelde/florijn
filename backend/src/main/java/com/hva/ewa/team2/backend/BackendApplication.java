package com.hva.ewa.team2.backend;

import com.hva.ewa.team2.backend.common.services.date.DateService;
import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.data.hourregistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.skill.ExpertiseRepository;
import com.hva.ewa.team2.backend.data.skill.SkillGroupRepository;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.user.Address;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final UserRepository userRepo;
    private final SkillRepository skillRepo;

    private final SkillGroupRepository skillGroupRepository;

    private final ExpertiseRepository expertiseRepository;

    private final ProjectRepository projectRepo;
    private final HourRegistrationRepository hourRegistrationRepo;
    private final DateService dateService;
    private final AvailabilityRepository availability;

    @Autowired
    public BackendApplication(UserRepository userRepo, SkillRepository skillRepo, SkillGroupRepository skillGroupRepository, ExpertiseRepository expertiseRepository, ProjectRepository projectRepo, HourRegistrationRepository hourRegistrationRepo, DateService dateService, AvailabilityRepository availability) {
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
        this.skillGroupRepository = skillGroupRepository;
        this.expertiseRepository = expertiseRepository;
        this.projectRepo = projectRepo;
        this.hourRegistrationRepo = hourRegistrationRepo;
        this.dateService = dateService;
        this.availability = availability;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        loadUsers();
        loadProjects();
        loadHourRegistrations();
        loadAvailabilities();
        loadSkills();
    }

    private void loadUsers() {
        userRepo.save(new Specialist(1, "withneyk@florijn.com", "test", "users/avatars/1.avif", "Withney", "Keulen"));
        userRepo.save(new Specialist(2, "jant@florijn.com", "test", "users/avatars/2.avif", "Jan", "Timmermans"));

        userRepo.save(new Admin(3, "admin@test.com", "test", null, "Admin", "Test"));
        userRepo.save(new Specialist(4, "specialist@test.com", "test", "users/avatars/3.avif", "Kingsley", "Mckenzie"));
        userRepo.save(new Client(5, "contact@ing.nl", "test", "users/avatars/5.webp", "ING", "users/banners/5.jpg"));

        Address dummyAddress1 = new Address("Amsterdam", "Jan van Galenstraat", 53, "E", "1204EX");
        Address dummyAddress2 = new Address("Hoorn", "Noorder Plantsoen", 12, "", "1623AB");

        setRandomSkills(new Specialist(6, "specialist2@test.com", "test", "users/avatars/6.png", "Sam", "Janssen", dummyAddress1));
        setRandomSkills(new Specialist(7, "specialist3@test.com", "test", "users/avatars/3.avif", "Jop", "Christensen", dummyAddress2));

        userRepo.save(new Client(8, "contact@microsoft.com", "test", "users/avatars/8.png", "Microsoft", "users/banners/8.jpg"));
    }

    private void loadProjects() {
        Client ingClient = (Client) userRepo.findById(5).orElse(null);

        Project ingProject = new Project(0,
                "ING Banking Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
                ingClient,
                "projects/logo-1.png",
                new ArrayList<>(), true);

        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(1).orElse(null), "Lead Developer", 60));
        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(2).orElse(null), "Designer", 40));

        projectRepo.save(ingProject);

        Project KPN = new Project(0,
                "KPN Network Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.", ingClient);

        KPN.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(1).orElse(null), "Lead Developer", 60));

        projectRepo.save(KPN);
    }

    private void loadSkills () {
        String[] skillNames = new String[]{"MS Office Access",
                "MS Office Access VBA",
                "MS Office Excel",
                "MS Office Excel VBA",
                "MS Power Query",
                "MS Powerpivot",
                "MS Office Word",
                "MS Office Word VBA",
                "MS Office Outlook",
                "MS Office Outlook VBA",
                "MS Office VBA",
                "MS SQL-Server",
                "MS SQL-Server Stored Procedures",
                "MS SQL-Server Views",
                "MY SQL",
                "MY SQL Workbench",
                "MS Azure",
                "Oracle",
                "Microsoft Dynamics AX",
                "Windows PowerShell (Core)",
                ".NET Framework",
                "XML - XAML",
                "Filemaker",
                "Filemaker Script",
                "Filemaker Server",
                "MS VB.NET",
                "MS Visual Basic",
                "Microsoft Dynamics 365",
                "Microsoft Dynamics 365 for Operations",
                "Microsoft Dynamics 365 for Business Applications",
                "Sharepoint",
                "Javascript",
                "Java",
                "PhP",
                "ASP.NET",
                "Google Apps ",
                "Google Apps Script",
                "Flow",
                "HTML",
                "CSS",
                "C#",
                "C++/CLI (Managed)",
                "F#",
                "Q#",
                ".NET Core",
                "Angular/AngularJS",
                "Bootstrap",
                "Mendix",
                "OutSystems",
                "PowerApps",
                "Power Automate",
                "Power Platform",
                "Tableau",
                "Qlik",
                "SAP",
                "SAS",
                "Oracle",
                "Salesforce",
                "Thoughtspot",
                "Yellowfin",
                "Sisense",
                "Microstrategy",
                "TIBCO Software",
                "Looker",
                "Information Builders",
                "Overall",
                "DAX",
                "M Language",
                "Grafisch",
                "Power Query",
                "Power BI Beheer",
                "Datamodellering",
                "Data analyse",
                "Agile",
                "Scrum",
                "Lean",
                "Kanban",
                "Extreme Programming (XP)"};

        String[] groupNames = new String[]{"OFFICE FRONT-END", "BACK-END", "DATABASE", "WEB BASED FRONT-END", "Business Intelligence","Power BI", "Werkwijze"};
        Integer[] skillPositions = {11, 11, 9, 21, 13, 8, 5};

        ArrayList<SkillGroup> skillGroups = new ArrayList<>();
        for (int i = 0; i < groupNames.length; i++) {
            SkillGroup skillGroup = new SkillGroup(i, groupNames[i], "This is the group for " + groupNames[i]);
            skillGroups.add(skillGroup);
            skillGroupRepository.save(skillGroup);
        }

        int index = 0;
        for (int i = 0; i < skillPositions.length; i++) {
            for (int j = 0; j < skillPositions[i]; j++) {
                Skill newSkill = new Skill(index, skillGroups.get(i), skillNames[i], "Your ability to use " + skillNames[i]);
                skillRepo.save(newSkill);
                index++;
            }
        }

        String[] expertiseNames = new String[]{"Financieel Administratief", "Hypotheken" ,"Facturatie/Offertes","Secutirisaties","Boekhouding","CRM","Rapportage-tools","Conversietools", "Workflow", "Logistieke Processen", "Engineering", "Bouw & Infra", "Marketing", "(semi) Overheidsinstelling", "Web/App Development"};

        for (int i = 0; i < expertiseNames.length; i++) {
            expertiseRepository.save(new Expertise(i, expertiseNames[i]));
        }
    }

    public void loadHourRegistrations() {
        System.out.println("=== IMPORTING HOURS ===");
        Project testProject = projectRepo.findById(1).orElse(null);

        final ProjectParticipant developer = testProject.getParticipantByUserId(1);
        final ProjectParticipant designer = testProject.getParticipantByUserId(2);

        hourRegistrationRepo.save(new HourRegistration(
                0,
                testProject,
                developer,
                dateService.currentDay(-2, 10, 0),
                dateService.currentDay(-2, 12, 0),
                "Gewerkt aan het project",
                HourRegistration.Status.ACCEPTED
        ));
        hourRegistrationRepo.save(new HourRegistration(
                0,
                testProject,
                designer,
                dateService.currentDay(-1, 8, 30),
                dateService.currentDay(-1, 12, 0),
                "Gewerkt aan het project",
                HourRegistration.Status.REJECTED
        ));
        hourRegistrationRepo.save(new HourRegistration(
                0,
                testProject,
                developer,
                dateService.currentDay(12, 15),
                dateService.currentDay(16, 0),
                "Gewerkt aan het project",
                HourRegistration.Status.ACCEPTED
        ));
        hourRegistrationRepo.save(new HourRegistration(
                0,
                testProject,
                developer,
                dateService.currentDay(8, 30),
                dateService.currentDay(12, 0),
                "Gewerkt aan het project"
        ));
        hourRegistrationRepo.save(new HourRegistration(
                0,
                testProject,
                designer,
                dateService.currentDay(13, 0),
                dateService.currentDay(17, 30),
                "Gewerkt aan het project"
        ));
    }

    public void loadAvailabilities() {
        availability.save(new Availability(
                userRepo.findById(1).orElse(null),
                LocalDate.now(),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        ));
        availability.save(new Availability(
                userRepo.findById(1).orElse(null),
                LocalDate.now(),
                LocalTime.of(13, 0),
                LocalTime.of(17, 0)
        ));
        availability.save(new Availability(
                userRepo.findById(1).orElse(null),
                LocalDate.now().plusDays(7),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        ));
        availability.save(new Availability(
                userRepo.findById(1).orElse(null),
                LocalDate.now().minusDays(7),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        ));
        availability.save(new Availability(
                userRepo.findById(1).orElse(null),
                LocalDate.now().minusDays(7).minusYears(1),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        ));
    }

    public Specialist setRandomSkills(Specialist specialist) {
        // Give specialist a random set of dummy skills
//        ArrayList<Skill> allSkills = skillRepo.findAllSkills();
//
//        // Shuffle skills
//        Collections.shuffle(allSkills);
//
//        // Get the size of half the list
//        int halfListSize = (int) Math.ceil((double) allSkills.size() / 2);
//
//        for (int i = 0; i < halfListSize; i++) {
//            int randomRating = ThreadLocalRandom.current().nextInt(1, 5 + 1);
//            // Add a skill to the specialist with a random rating
//            specialist.updateUserSkill(allSkills.get(i), randomRating);
//        }
//
//        ArrayList<Expertise> allExpertises = skillRepo.getAllExpertises();
//
//        ArrayList<UserExpertise> userExpertises = new ArrayList<>();
//        int specialistId = specialist.getId();
//        for (Expertise expertise : allExpertises) {
//            boolean randomBool = ThreadLocalRandom.current().nextBoolean();
//            if (randomBool == true) {
//                userExpertises.add(new UserExpertise(expertise.getId(), specialistId));
//            }
//        }
//
//        System.out.println(userExpertises);
//
//        specialist.updateUserExpertise(userExpertises);

        return userRepo.save(specialist);
    }

}
