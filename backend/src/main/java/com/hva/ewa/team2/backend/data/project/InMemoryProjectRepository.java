package com.hva.ewa.team2.backend.data.project;

import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryProjectRepository implements ProjectRepository {

    private final ArrayList<Project> projectList;

    private final UserRepository userRepo;

    @Autowired
    public InMemoryProjectRepository(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.projectList = new ArrayList<>();

        setup();
    }

    private void setup() {
        Client ingClient = (Client) userRepo.findById(4);

        Project ingProject = new Project(1,
                "ING Banking Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
                ingClient,
                "/src/assets/ING-Bankieren-icoon.webp");

        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(0), "Lead Developer", 60));
        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.findById(1), "Designer", 40));
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
