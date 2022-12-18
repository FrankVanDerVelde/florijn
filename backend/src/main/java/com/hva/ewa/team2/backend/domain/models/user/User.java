package com.hva.ewa.team2.backend.domain.models.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    public String email;

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
//    @Column(columnDefinition = ("varchar(255) default 'defaults/default-avatar.png'"))
    public String avatarUrl;

    @Getter
    @Setter
    public String password;

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    public Role role;

    public User() {
    }

    public User(int id, String email, String password, String profilePictureURL, Role role) {
        this.id = id;
        this.email = email;
        if (profilePictureURL == null) {
            profilePictureURL = "defaults/default-avatar.png";
        }
        this.avatarUrl = profilePictureURL;
        this.password = password;
        this.role = role;
    }

    public enum Role {
        ADMIN(Admin.class),
        SPECIALIST(Specialist.class),
        CLIENT(Client.class);

        private final Class<? extends User> userClass;

        Role(Class<? extends User> userClass) {
            this.userClass = userClass;
        }

        public Class<? extends User> getUserClass() {
            return userClass;
        }
    }

    public class EssentialInfo {

    }

}
