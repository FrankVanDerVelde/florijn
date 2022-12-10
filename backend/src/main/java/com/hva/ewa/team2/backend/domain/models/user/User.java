package com.hva.ewa.team2.backend.domain.models.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "User.findUsersByRole", query = "select u from User u where u.role = ?1"),
        @NamedQuery(name = "User.getUserInfoByCredentials", query = "select u from User u where u.email = ?1 and u.password = ?2")
})
public abstract class User {

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    protected String email;
    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    protected String avatarUrl;
    @Getter
    @Setter
    protected String password;
    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    protected Role role;

    public User() {}

    public User(int id, String email, String password, String profilePictureURL, Role role) {
        this.id = id;
        this.email = email;
        this.avatarUrl = profilePictureURL;
        this.password = password;
        this.role = role;
    }

    public enum Role {
        ADMIN,
        SPECIALIST,
        CLIENT
    }

    public class EssentialInfo {

    }

}
