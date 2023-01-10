package com.hva.ewa.team2.backend.domain.models.project;

import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
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

    public ProjectParticipant getParticipantByUserId(Integer userId) {
        return participants.stream()
                .filter(p -> p.getSpecialist().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", client=" + client +
                ", logoSrc='" + logoSrc + '\'' +
                ", archived=" + archived +
                ", participants=" + participants +
                '}';
    }

    public void addSpecialist(ProjectParticipant specialist) {
        participants.add(specialist);
    }

    public boolean removeSpecialist(ProjectParticipant specialist) {
        return participants.remove(specialist);
    }

    public boolean hasAccess(User user, boolean requiresModerationPerm) {
        return user != null && (user instanceof Admin ||
                this.client.getId().equals(user.getId()) ||
                (!requiresModerationPerm && this.participants.stream().anyMatch(p -> p.getSpecialist().getId().equals(user.getId()))
                ));
    }

    public static class EssentialInfo extends User.EssentialInfo {
    }

}
