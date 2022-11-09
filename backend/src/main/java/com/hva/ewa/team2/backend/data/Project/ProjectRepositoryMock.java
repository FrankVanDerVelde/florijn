package com.hva.ewa.team2.backend.data.Project;

import com.hva.ewa.team2.backend.data.Specialist.SpecialistRepositoryMock;
import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Specialist.Specialist;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryMock implements ProjectRepository {

    private ArrayList<Project> projectList;
    private List<Specialist> specialist = new SpecialistRepositoryMock().findAll();

    public ProjectRepositoryMock() {
        List<Project> projectList =  List.of(
                new Project(
                        0,
                        "Project 1",
                        "This is the first project",
                        specialist

                ),
                new Project(
                        0,
                        "Project 1",
                        "This is the first project",
                        specialist
                ));

        this.projectList = new ArrayList<>(projectList);
    }

    public List<Project> findAll() {
            return projectList;
    }

    public Project findById(long id) {
        return null;
    }

    public Project insert() {
        return null;
    }

    public Project update() {
        return null;
    }

    public Project delete() {
        return null;
    }
}
