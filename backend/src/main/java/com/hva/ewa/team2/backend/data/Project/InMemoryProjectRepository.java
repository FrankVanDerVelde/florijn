package com.hva.ewa.team2.backend.data.Project;

import com.hva.ewa.team2.backend.data.Specialist.SpecialistRepositoryMock;
import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryProjectRepository implements ProjectRepository {

    private final ArrayList<Project> projectList;

    public InMemoryProjectRepository() {
        List<Specialist> specialist = new SpecialistRepositoryMock().findAll();
        Client client = new Client(48, "contact@ing.nl", "/src/assets/ING-Bankieren-icoon.webp", "ING");
        // TODO: Get client(s) from repository instead of making one on the spot.

        Project ING = new Project(1,
                "ING Banking Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
                client);
        ING.addSpecialist(new ProjectParticipant(specialist.get(0), "Lead Developer", 60));
        ING.addSpecialist(new ProjectParticipant(specialist.get(1), "Designer", 40));

        // TODO: Add more projects.
        this.projectList = new ArrayList<>(List.of(ING));
    }

    @Override
    public List<Project> findAll() {
        return projectList;
    }

    @Override
    public Project findById(long id) {
        return this.projectList.stream()
                .filter(project -> project.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Project addProject(Project project) {
        if (findById(project.getId()) != null) {
            throw new IllegalArgumentException("A project with that ID already exists.");
        }

        if (project.getId() <= 0) {
            project.setId(this.projectList.size() + 1);
        }

        this.projectList.add(project);
        return project;
    }

    @Override
    public boolean projectExists(int id) {
        return findById(id) != null;
    }

    @Override
    public Project updateProject(Project project) {
        final Project found = findById(project.getId());
        if (found == null) {
            throw new IllegalArgumentException("A project with that ID does not exist.");
        }

        // updating only specific fields when found.
        found.setTitle(project.getTitle());
        found.setDescription(project.getDescription());
        found.setClient(project.getClient());

        return found;
//        // replacing all projects with the given ID with the provided project, otherwise with themselves to change nothing.
//        this.projectList.replaceAll(p -> p.getId() == project.getId() ? project : p);
    }

    @Override
    public boolean deleteProject(int id) {
        return this.projectList.removeIf(p -> p.getId() == id);
    }

    @Override
    public List<Project> getProjectsByClient(int clientId) {
        return this.projectList.stream()
                .filter(p -> p.getClient().getId() == clientId)
                .toList();
    }

    @Override
    public List<Project> getProjectsBySpecialist(int specialistId) {
        return this.projectList.stream()
                .filter(p -> p.getParticipants().stream().anyMatch(pp -> pp.getSpecialist().getId() == specialistId))
                .toList();
    }
}
