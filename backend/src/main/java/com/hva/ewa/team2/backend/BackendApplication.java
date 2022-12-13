package com.hva.ewa.team2.backend;

import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.Address;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final UserRepository userRepo;
    private final SkillRepository skillRepo;

    @Autowired
    public BackendApplication(UserRepository userRepo, SkillRepository skillRepo) {
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        userRepo.save(new Specialist(0, "withneyk@florijn.com", "test", "/src/assets/avatars/avatar2.avif", "Withney", "Keulen"));
        userRepo.save(new Specialist(1, "jant@florijn.com", "test", "/src/assets/avatars/avatar3.avif", "Jan", "Timmermans"));

        userRepo.save(new Admin(2, "admin@test.com", "test", null, "Admin", "Test"));
        userRepo.save(new Specialist(3, "specialist@test.com", "test", "/src/assets/avatars/avatar3.avif", "Kingsley", "Mckenzie"));
        userRepo.save(new Client(4, "contact@ing.nl", "test", "/src/assets/ING-Bankieren-icoon.webp", "ING", "/src/assets/ing-banner.jpg"));

        Address dummyAddress1 = new Address("Amsterdam", "Jan van Galenstraat", 53, "E", "1204EX");
        Address dummyAddress2 = new Address("Hoorn", "Noorder Plantsoen", 12, "", "1623AB");

        setRandomSkills(new Specialist(5, "specialist2@test.com", "test", "/src/assets/avatars/avatar3.avif", "Sam", "Janssen", dummyAddress1));
        setRandomSkills(new Specialist(6, "specialist3@test.com", "test", "/src/assets/avatars/avatar3.avif", "Jop", "Christensen", dummyAddress2));

        userRepo.save(new Client(7, "contact@microsoft.com", "test", "/src/assets/microsoft-logo.png", "Microsoft", "/src/assets/microsoft-banner.jpeg"));
    }


    public void setRandomSkills(Specialist specialist) {
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

        userRepo.save(specialist);
    }

}
