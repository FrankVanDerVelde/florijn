package com.hva.ewa.team2.backend;

import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.usecases.project.ProjectInteractor;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
public class BackendProjectsTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProjectInteractor interactor;

    @Test
    public void listOfProjectsIsValid() {
        final List<Project> allProjects = interactor.getAllProjects();
        assertNotNull(allProjects, "List of projects should not be null");
        assertEquals(1, allProjects.size(), "There should be 1 project in the database");
        assertInstanceOf(Project.class, allProjects.get(0), "The list objects should be of type Project");
    }

    @Test
    public void canCreateProject() {
        final IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () ->
                interactor.createProject(new JsonProjectInfo("", "", -1)));
        assertEquals("The project title cannot be empty.", ex1.getMessage());

        final IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () ->
                interactor.createProject(new JsonProjectInfo("test", "", -1)));
        assertEquals("The project client id is invalid.", ex2.getMessage());

        final IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () ->
                interactor.createProject(new JsonProjectInfo("test", "", 1)));
        assertEquals("The project description cannot be empty.", ex3.getMessage());

        final Project createdProject = interactor.createProject(new JsonProjectInfo("test", "test2", 1));

        assertNotNull(createdProject, "The created project should not be null");
        assertEquals(2, createdProject.getId(), "The created project should have an ID of 2");
        assertEquals("test", createdProject.getTitle(), "The created project should have the title 'test'");
        assertEquals("test2", createdProject.getDescription(), "The created project should have the description 'test2'");
        assertNotNull(createdProject.getClient(), "The created project should have a client");
        assertEquals(1, createdProject.getClient().getId(), "The created project should have a client with ID 1");
        assertEquals(0, createdProject.getParticipants().size(), "The created project should have no participants");

        assertEquals(2, interactor.getAllProjects().size(), "There should be 2 projects in the database");
    }

    @Test
    public void getProjectInformation() {
        final IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> interactor.getProjectInformation(-6));
        assertEquals("The project ID cannot be negative.", ex1.getMessage());

        final IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> interactor.getProjectInformation(130));
        assertEquals("The project with ID 130 does not exist.", ex2.getMessage());

        final Project project = interactor.getProjectInformation(1);

        assertNotNull(project, "The project should not be null");
        assertEquals(1, project.getId(), "The project should have an ID of 1");
        assertEquals(2, project.getParticipants().size(), "The project should have 2 participants");
        assertEquals("ING Banking Web Application", project.getTitle());
        assertEquals("Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.", project.getDescription());
        assertEquals(project.getClient().getName(), "ING");
    }

    @Test
    public void updateProjectInformation() {
        JsonProjectInfo info = new JsonProjectInfo("ING Forever", "The future of ING.", 5);

        final IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> interactor.updateProjectInformation(99, info));
        assertEquals("The project with ID 99 does not exist.", ex1.getMessage());

        Project updated = interactor.updateProjectInformation(1, info);
        assertEquals(info.getTitle(), updated.getTitle());
        assertEquals(info.getDescription(), updated.getDescription());
        assertEquals(info.getClient(), updated.getClient().getId());
    }

    @Test
    public void getParticipants() {
        final IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> interactor.getProjectParticipants(-1));
        assertEquals("The project with ID -1 does not exist.", ex1.getMessage());

        final List<ProjectParticipant> participants = interactor.getProjectParticipants(1);
        assertEquals(2, participants.size());
        assertInstanceOf(ProjectParticipant.class, participants.get(0));


        final IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> interactor.getProjectParticipant(1, 199));
        assertEquals("The user with ID 199 is not a participant of the project with ID 1.", ex2.getMessage());

        final ProjectParticipant participant = interactor.getProjectParticipant(1, 1);
        assertEquals(1, participant.getSpecialist().getId());


        final IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () ->
                interactor.addProjectParticipant(1, new JsonProjectParticipantAddInfo(99, "Tester", 45.00)));
        assertEquals("The user with ID 99 does not exist or is not a specialist.", ex3.getMessage());

        final IllegalArgumentException ex4 = assertThrows(IllegalArgumentException.class, () ->
                interactor.addProjectParticipant(1, new JsonProjectParticipantAddInfo(1, "Tester", 45.00)));
        assertEquals("User 1 is already participating this project.", ex4.getMessage());

        interactor.removeProjectParticipant(1, 1);

        assertEquals(1, interactor.getProjectParticipants(1).size());

        final ProjectParticipant newParticipant = interactor.addProjectParticipant(1, new JsonProjectParticipantAddInfo(1, "Tester", 45.00));

        assertEquals(2, interactor.getProjectParticipants(1).size());
        assertEquals(1, newParticipant.getSpecialist().getId());
        assertEquals("Tester", newParticipant.getRole());
        assertEquals(45.00, newParticipant.getHourlyRate());


    }

}
