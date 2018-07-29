package de.brockhausag.gruntr.data.entities;

import de.brockhausag.gruntr.auth.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String passwordHash;
    private UserRole role;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = GruntEntity.class)
    private List<GruntEntity> posts = new ArrayList<>();

    //<editor-fold desc="getters and setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<GruntEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<GruntEntity> posts) {
        this.posts = posts;
    }
    //</editor-fold>
}
