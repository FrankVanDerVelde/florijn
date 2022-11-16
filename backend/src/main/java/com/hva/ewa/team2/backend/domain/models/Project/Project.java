package com.hva.ewa.team2.backend.domain.models.Project;

import com.hva.ewa.team2.backend.domain.models.User.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Project {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private Client client;
    @Getter @Setter
    private List<ProjectParticipant> participants;

    public Project(int id, String title, String description, Client client) {
        this(id, title, description, client, new ArrayList<>());
    }

    public Project(int id, String title, String description, Client client, List<ProjectParticipant> specialists) {
        this.id = id;
        this.title = title;
        this.client = client;
        this.description = description;
        this.participants = specialists;
    }

    public ProjectParticipant getParticipantById(int id) {
        return participants.stream()
                .filter(p -> p.getSpecialist().getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addSpecialist(ProjectParticipant specialist) {
        participants.add(specialist);
    }

    public boolean removeSpecialist(ProjectParticipant specialist) {
        return participants.remove(specialist);
    }

}
