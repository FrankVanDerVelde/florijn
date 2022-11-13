package com.hva.ewa.team2.backend.domain.usecases.project;

import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;

import java.util.List;

public class ProjectLogic implements ProjectBusinessLogic{

    // TODO: implement the project logic.

    @Override
    public Project createProject(Project project) {
        return null;
    }

    @Override
    public Project getProjectInformation(int id) {
        return null;
    }

    @Override
    public boolean deleteProject(int id) {
        return false;
    }

    @Override
    public Project updateProject(Project project) {
        return null;
    }

    @Override
    public List<ProjectParticipant> getProjectParticipants(int id) {
        return null;
    }

    @Override
    public boolean removeProjectParticipant(int projectId, int userId) {
        return false;
    }

    @Override
    public ProjectParticipant addProjectParticipant(int projectId, int userId, String role, double hourlyRate) {
        return null;
    }
}
