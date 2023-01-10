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
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import com.hva.ewa.team2.backend.domain.models.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.EntityManagerHolder;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        loadSkills();
        loadUsers();
        loadProjects();
        loadHourRegistrations();
        loadAvailabilities();
        setRandomSkillList();
    }

    private void loadUsers() {
        userRepo.save(new Admin(11, "admin@test.com", "test", "users/avatars/admin1.jpeg", "Ysabella", "de Jong"));
        userRepo.save(new Admin(12, "admin2@test.com", "test", "users/avatars/admin2.jpeg", "Hans", "Oudekerk"));

        // clients
        userRepo.save(new Client(13, "contact@ing.nl", "test", "users/avatars/5.webp", "ING", "users/banners/5.jpg"));
        userRepo.save(new Client(14, "contact@microsoft.com", "test", "users/avatars/8.png", "Microsoft", "users/banners/8.jpg"));
        userRepo.save(new Client(15, "contact@mediamarkt.com", "test", "users/avatars/mediamarktlogo.png", "Media Markt", "users/avatars/mediamarktbanner.jpg"));

        // specialist
        userRepo.save(
                new Specialist(1
                , "specialist1@test.com",
                        "test",
                        "users/avatars/1.avif",
                        "Whitney", "Keulen",
                        new Address(
                                "Hoorn",
                                "Noorder Plantsoen",
                                12,
                                "",
                                "1623AB"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(2,
                "specialist2@test.com",
                        "test",
                        "users/avatars/specialist2.jpeg",
                        "Jan", "Timmermans",
                        new Address(
                                "Diemen-Zuid",
                                "Gert Jan Straat",
                                14,
                                "A",
                                "1133XJ"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(3,
                "specialist3@test.com",
                        "test",
                        "users/avatars/specialist3.jpeg",
                        "Kingsley", "Mckenzie",
                        new Address(
                                "Diemen-Noord",
                                "Beren straat",
                                31,
                                "B",
                                "1432LM"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(
                        4,
                        "specialist4@test.com",
                        "test",
                        "users/avatars/specialist4.jpeg",
                        "Albert",
                        "van Hof",
                        new Address(
                                "Doordrecht",
                                "Wester plas",
                                63,
                                "",
                                "1104EX"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(
                        5,
                        "specialist5@test.com",
                        "test",
                        "users/avatars/specialist5.jpeg",
                        "Anna",
                        "Arends",
                        new Address(
                                "Groningen",
                                "Groniger straat",
                                13,
                                "A",
                                "2504EX"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(
                        6,
                        "specialist6@test.com",
                        "test",
                        "users/avatars/specialist6.jpeg",
                        "Ben",
                        "de Wilde",
                        new Address(
                                "Avenhorn",
                                "Westerkogge straat",
                                13,
                                "",
                                "5230PL"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(
                        7,
                        "specialist7@test.com",
                        "test",
                        "users/avatars/specialist7.jpeg",
                        "David",
                        "de Wit",
                        new Address(
                                "Maastricht",
                                "Oesterstraat",
                                1233,
                                "",
                                "1264AX"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(
                        8,
                        "specialist8@test.com",
                        "test",
                        "users/avatars/specialist8.jpeg",
                        "Gloria",
                        "Gonzales",
                        new Address(
                                "Bergen op Soom",
                                "Bergenstraat",
                                123,
                                "",
                                "7264AL"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(
                        9,
                        "specialist9@test.com",
                        "test",
                        "users/avatars/specialist9.jpeg",
                        "Jannie",
                        "Verstegen",
                        new Address(
                                "Middenmeer",
                                "Leegwater straat",
                                23,
                                "",
                                "1264AB"),
                        "users/resumes/sample-resume.pdf"));

        userRepo.save(
                new Specialist(
                        10,
                        "specialist10@test.com",
                        "test",
                        "users/avatars/specialist10.jpeg",
                        "Kim",
                        "Whitfield",
                        new Address(
                                "Arnhem",
                                "Gelrestraat",
                                63,
                                "H",
                                "2364AB"),
                        "users/resumes/sample-resume.pdf"));

    }

    private void loadProjects() {
        Client ingClient = (Client) userRepo.findById(5).orElse(null);

        Project ingProject = new Project(0,
                "ING Banking Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
                ingClient,
                "projects/logo-1.png",
                new ArrayList<>(), true);

        // TODO readd user 1
//        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(1).orElse(null), "Lead Developer", 60));
//        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(2).orElse(null), "Designer", 40));

        projectRepo.save(ingProject);

        Project KPN = new Project(0,
                "KPN Network Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.", ingClient);

//        KPN.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(1).orElse(null), "Lead Developer", 60));

        projectRepo.save(KPN);
    }

    private void loadSkills () {
        String[] skillNames = new String[]{
                "MS Office Access",
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

        for (String groupName : groupNames) {
            SkillGroup skillGroup = new SkillGroup(0, groupName, "This is the group for " + groupName);
            skillGroupRepository.save(skillGroup);
        }

        int index = 0;
        for (int i = 0; i < skillPositions.length; i++) {
            for (int j = 0; j < skillPositions[i]; j++) {
                Skill newSkill = new Skill(0, skillGroupRepository.findGroupById(i + 1), skillNames[index], "Your ability to use " + skillNames[index]);
                skillRepo.save(newSkill);
                index++;
            }
        }

        String[] expertiseNames = new String[]{"Financieel Administratief", "Hypotheken" ,"Facturatie/Offertes","Secutirisaties","Boekhouding","CRM","Rapportage-tools","Conversietools", "Workflow", "Logistieke Processen", "Engineering", "Bouw & Infra", "Marketing", "(semi) Overheidsinstelling", "Web/App Development"};

        for (String expertiseName : expertiseNames) {
            expertiseRepository.save(new Expertise(0, expertiseName));
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

    public void setRandomSkillList() {
        List<Specialist> specialists = userRepo.findUsersByRole(User.Role.SPECIALIST, Specialist.class);
        List<Skill> skills = skillRepo.getAllSkills();
        ArrayList<Expertise> expertises = expertiseRepository.findAllExpertises();

        for (Specialist specialist: specialists) {
            // Shuffle skills
            Collections.shuffle(skills);

            // Get the size of half the list
            int halfListSize = (int) Math.ceil((double) skills.size() / 2);

            ArrayList<UserSkill> userSkills = new ArrayList<>();
            for (int i = 0; i < halfListSize; i++) {
                int randomRating = ThreadLocalRandom.current().nextInt(1, 5 + 1);
                // Add a skill to the specialist with a random rating
                userSkills.add(new UserSkill(0, specialist, skills.get(i), randomRating));

            }
            specialist.setSkills(userSkills);

            ArrayList<Expertise> userExpertises = new ArrayList<>();
            for (Expertise expertise : expertises) {
                boolean randomBool = ThreadLocalRandom.current().nextBoolean();
                if (randomBool) {
                    userExpertises.add(expertise);
                }
            }

            specialist.setExpertises(userExpertises);

            userRepo.save(specialist);
        }

    }

}
