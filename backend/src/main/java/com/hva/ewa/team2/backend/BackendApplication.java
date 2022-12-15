package com.hva.ewa.team2.backend;

import com.hva.ewa.team2.backend.common.services.date.DateService;
import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.data.hourregistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.skill.MemorySkillRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
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
    private final MemorySkillRepository skillRepo;
    private final ProjectRepository projectRepo;
    private final HourRegistrationRepository hourRegistrationRepo;
    private final DateService dateService;
    private final AvailabilityRepository availability;

    @Autowired
    public BackendApplication(UserRepository userRepo,
                              MemorySkillRepository skillRepo,
                              ProjectRepository projectRepo,
                              HourRegistrationRepository hourRegistrationRepo,
                              DateService dateService,
                              AvailabilityRepository availability) {
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
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
