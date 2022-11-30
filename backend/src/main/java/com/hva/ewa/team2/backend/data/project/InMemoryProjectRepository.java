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

    private ArrayList<Project> projectList;

    private final UserRepository userRepo;

    @Autowired
    public InMemoryProjectRepository(UserRepository userRepo) {
        this.userRepo = userRepo;
        this.projectList = new ArrayList<>();

        setup();
    }

    private void setup() {
        Client ingClient = (Client) userRepo.getUserById(4);

        Project ingProject = new Project(1,
                "ING Banking Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.",
                ingClient,
                "projects/logo-1.png",
                new ArrayList<>(), true);

        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.getUserById(0), "Lead Developer", 60));
        ingProject.addSpecialist(new ProjectParticipant((Specialist) userRepo.getUserById(1), "Designer", 40));

        projectList.add(ingProject);
        Project KPN = new Project(2,
                "KPN Network Web Application",
                "Website ontwikkeling voor Florijn. Hier komt een korte beschrijving van het project.", ingClient);

        KPN.addSpecialist(new ProjectParticipant((Specialist) userRepo.getUserById(0), "Lead Developer", 60));


        // TODO: Add more projects.
        this.projectList = new ArrayList<>(List.of(ingProject, KPN));
    }

    @Override
    public List<Project> findAll() {
        return projectList;
    }

    @Override
    public Project findById(int id) {
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
        found.setLogoSrc(project.getLogoSrc());

        return found;
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
