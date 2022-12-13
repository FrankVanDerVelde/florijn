package com.hva.ewa.team2.backend.domain.models.project;

import com.hva.ewa.team2.backend.domain.models.user.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Getter
    @Setter
    private String logoSrc;
    @Getter
    @Setter
    private boolean archived;

    @Getter
    @Setter
    @OneToMany(mappedBy = "projectId", cascade = CascadeType.ALL)
    private List<ProjectParticipant> participants;

    public Project() {
    }

    public Project(int id, String title, String description, Client client) {
        this(id, title, description, client, "projects/sample-logo.png", new ArrayList<>(), false);
    }

    public Project(int id, String title, String description, Client client, String logoSrc) {
        this(id, title, description, client, logoSrc, new ArrayList<>(), false);
    }

    public Project(int id, String title, String description, Client client, String logoSrc, List<ProjectParticipant> specialists, boolean archived) {
        this.id = id;
        this.title = title;
        this.client = client;
        this.description = description;
        this.logoSrc = logoSrc;
        this.participants = specialists;
        this.archived = archived;
    }

    public ProjectParticipant getParticipantByUserId(int userId) {
        return participants.stream()
                .filter(p -> p.getSpecialist().getId() == userId)
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
