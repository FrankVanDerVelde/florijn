package com.hva.ewa.team2.backend.lukas.repository;

import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;

    private Project project1;

    @BeforeEach
    public void setup() {
        final Client ing = userRepository.save(new Client(0, "test@test.com", "test", "", "ING"));
        this.project1 = projectRepository.save(new Project(0, "Hello Holland", "i love Holland", ing));
    }

    @Test
    public void testFindAll() {
        final ArrayList<Project> projects = projectRepository.findAll();

        final Optional<Project> found = projectRepository.findById(project1.getId());

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(project1.getTitle(), found.get().getTitle());

        Assertions.assertTrue(projects.contains(project1));
    }

    @Test
    public void testProjectSearch() {
        final Project project2 = projectRepository.save(new Project(0, "Who knows", "netherlands is what I hate", project1.getClient()));
        final Project project3 = projectRepository.save(new Project(0, "Love is in the air", "netherlands is what I hate", project1.getClient()));
        final Project project4 = projectRepository.save(new Project(0, "Bonjour", "oui oui", project1.getClient()));

        // getting all projects with "love" in the title or description.
        final ArrayList<Project> projects = projectRepository.findAllByQuery("love");

        // making sure only those with "love" are returned.
        Assertions.assertTrue(projects.contains(project1));
        Assertions.assertFalse(projects.contains(project2));
        Assertions.assertTrue(projects.contains(project3));
        Assertions.assertFalse(projects.contains(project4));
    }

}
