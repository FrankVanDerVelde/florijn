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
import javax.print.attribute.standard.Media;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
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
        // checking if the user table is filled with data, if so, cancel seeding.
        if (!userRepo.findAll().isEmpty()) return;

        loadSkills();
        loadUsers();
        loadProjects();
        loadHourRegistrations();
        loadAvailabilities();
        setRandomSkillList();
    }

    private void loadUsers() {

        // admins
        userRepo.save(new Admin(0, "admin1@test.com", "test", "users/avatars/admin1.jpeg", "Maria", "de Jong"));
        userRepo.save(new Admin(0, "admin2@test.com", "test", "users/avatars/admin2.jpeg", "Hans", "Oudekerk"));

        // clients
        userRepo.save(new Client(0, "client1@test.com", "test", "users/avatars/5.webp", "ING", "users/banners/5.jpg"));
        userRepo.save(new Client(0, "client2@test.com", "test", "users/avatars/8.png", "Microsoft", "users/banners/8.jpg"));
        userRepo.save(new Client(0, "client3@test.com", "test", "users/avatars/mediamarktlogo.png", "Media Markt", "users/banners/mediamarktbanner.jpg"));
        userRepo.save(new Client(0, "client4@test.com", "test", "users/avatars/kpn-logo.png", "KPN", "users/banners/kp-banner.jpeg"));

        // specialists
        userRepo.save(
                new Specialist(0
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
                new Specialist(0,
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
                new Specialist(0,
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
                        0,
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
                        0,
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
                        0,
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
                        0,
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
                        0,
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
                        0,
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
        List<Client> allClients = userRepo.findUsersByRole(User.Role.CLIENT, Client.class);
        List<Specialist> allSpecialist = userRepo.findUsersByRole(User.Role.SPECIALIST, Specialist.class);

        Client ingClient = allClients.stream().filter(client -> client.getName().equals("ING")).findAny().orElse(null);
        Client microsoftClient = allClients.stream().filter(client -> client.getName().equals("Microsoft")).findAny().orElse(null);
        Client mediaMarktClient = allClients.stream().filter(client -> client.getName().equals("Media Markt")).findAny().orElse(null);
        Client KPNClient = allClients.stream().filter(client -> client.getName().equals("KPN")).findAny().orElse(null);

        // ING
        Project ingProject = new Project(
                0,
                "ING bankier web applicatie",
                "De nieuwe web applicatie voor internet bankieren bij ING.",
                ingClient,
                "projects/9a160d9b-0817-4d87-b6e6-441fcd8e8329.webp",
                new ArrayList<>(),
                true);

        ingProject.addSpecialist(new ProjectParticipant(allSpecialist.get(0), "Lead Developer", 60));
        ingProject.addSpecialist(new ProjectParticipant(allSpecialist.get(1), "Designer", 40));

        projectRepo.save(ingProject);

        // Microsoft
        Project Microsoft = new Project(0,
                "Microsoft smart design",
                "The latest all in one design tool, for any kind of product be it digital or analog.",
                microsoftClient);

        Microsoft.addSpecialist(new ProjectParticipant(allSpecialist.get(2), "Senior developer", 80));
        Microsoft.addSpecialist(new ProjectParticipant(allSpecialist.get(3), "Senior designer", 80));

        projectRepo.save(Microsoft);

        Project MediaMarkt = new Project(0,
                "Media markt VR application",
                "De nieuwe VR applicatie voor een compleet nieuwe moderne winkel ervaring. Met deze applicatie zal het mogenlijk worden om een digitale media markt winkel te bezoeken in VR, producten te bekijken en uit te proberen en ze vervolgens te bestellen.",
                mediaMarktClient);

        MediaMarkt.addSpecialist(new ProjectParticipant(allSpecialist.get(4), "VR specialist", 120));
        MediaMarkt.addSpecialist(new ProjectParticipant(allSpecialist.get(5), "3D animator", 100));

        projectRepo.save(MediaMarkt);

        Project KPN = new Project(0,
                "KPN all in one service manager",
                "De nieuwe service manager voor KPN telefoneren, internet en tv.",
                KPNClient,
                "projects/974dafd7-5324-4f65-8ceb-36e1b0f2587e.png");

        KPN.addSpecialist(new ProjectParticipant(allSpecialist.get(6), "Network specialist", 50));
        KPN.addSpecialist(new ProjectParticipant(allSpecialist.get(7), "Designer", 60));

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
        List<Project> allProjects = projectRepo.findAll();

        for (Project project: allProjects) {
            List<ProjectParticipant> projectOneMembers = project.getParticipants();
            System.out.println(project.getParticipants());
            hourRegistrationRepo.save(new HourRegistration(
                    0,
                    project,
                    projectOneMembers.get(0),
                    dateService.currentDay(-2, 10, 0),
                    dateService.currentDay(-2, 12, 0),
                    "Worked on back-end",
                    HourRegistration.Status.ACCEPTED
            ));
            hourRegistrationRepo.save(new HourRegistration(
                    0,
                    project,
                    projectOneMembers.get(1),
                    dateService.currentDay(-1, 8, 30),
                    dateService.currentDay(-1, 12, 0),
                    "Created a house style and logo",
                    HourRegistration.Status.REJECTED
            ));
            hourRegistrationRepo.save(new HourRegistration(
                    0,
                    project,
                    projectOneMembers.get(0),
                    dateService.currentDay(12, 15),
                    dateService.currentDay(16, 0),
                    "Worked on front-end",
                    HourRegistration.Status.ACCEPTED
            ));
            hourRegistrationRepo.save(new HourRegistration(
                    0,
                    project,
                    projectOneMembers.get(0),
                    dateService.currentDay(8, 30),
                    dateService.currentDay(12, 0),
                    "Created rest api routes"
            ));
            hourRegistrationRepo.save(new HourRegistration(
                    0,
                    project,
                    projectOneMembers.get(1),
                    dateService.currentDay(13, 0),
                    dateService.currentDay(17, 30),
                    "Designed landing page"
            ));
        }

    }

    public void loadAvailabilities() {
        List<Specialist> allSpecialist = userRepo.findUsersByRole(User.Role.SPECIALIST, Specialist.class);

        LocalDate dateTwoWeeksFromAgo = LocalDate.now().minusWeeks(2);
        LocalDate dateThreeWeeksFromNow = LocalDate.now().plusWeeks(3);
        Random random = new Random();

        for (Specialist specialist: allSpecialist) {
            for (LocalDate date = dateTwoWeeksFromAgo; date.isBefore(dateThreeWeeksFromNow); date = date.plusDays(1)) {
                if (date.getDayOfWeek().getValue() < 6) {
                    if (random.nextBoolean()) {
                        int randomMorningHour = (int) ((Math.random() * (12 - 6)) + 6);
                        int randomAfternoonHour = (int) ((Math.random() * (22 - 15)) + 15);
                        int randomMinutesOne = (int) ((Math.random() * (4 - 0)) + 0) * 15;
                        int randomMinutesTwo = (int) ((Math.random() * (4 - 0)) + 0) * 15;

                        availability.save(new Availability(
                                specialist,
                                date,
                                LocalTime.of(randomMorningHour, randomMinutesOne),
                                LocalTime.of(randomAfternoonHour, randomMinutesTwo)
                        ));
                    }
                }
            }
        }

//        availability.save(new Availability(
//                userRepo.findById(1).orElse(null),
//                LocalDate.now(),
//                LocalTime.of(8, 0),
//                LocalTime.of(12, 0)
//        ));
//        availability.save(new Availability(
//                userRepo.findById(1).orElse(null),
//                LocalDate.now(),
//                LocalTime.of(13, 0),
//                LocalTime.of(17, 0)
//        ));
//        availability.save(new Availability(
//                userRepo.findById(1).orElse(null),
//                LocalDate.now().plusDays(7),
//                LocalTime.of(8, 0),
//                LocalTime.of(12, 0)
//        ));
//        availability.save(new Availability(
//                userRepo.findById(1).orElse(null),
//                LocalDate.now().minusDays(7),
//                LocalTime.of(8, 0),
//                LocalTime.of(12, 0)
//        ));
//        availability.save(new Availability(
//                userRepo.findById(1).orElse(null),
//                LocalDate.now().minusDays(7).minusYears(1),
//                LocalTime.of(8, 0),
//                LocalTime.of(12, 0)
//        ));
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
