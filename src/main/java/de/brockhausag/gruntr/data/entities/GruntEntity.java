package de.brockhausag.gruntr.data.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GruntEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Instant postedOn;
    @JoinColumn(name = "authorid")
    @ManyToOne()
    private UserEntity author;
    private String content;
    @OneToMany(targetEntity = GruntEntity.class)
    private List<GruntEntity> replies = new ArrayList<>();

    //<editor-fold desc="getters and setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Instant postedOn) {
        this.postedOn = postedOn;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<GruntEntity> getReplies() {
        return replies;
    }

    public void setReplies(List<GruntEntity> replies) {
        this.replies = replies;
    }
    //</editor-fold>
}
