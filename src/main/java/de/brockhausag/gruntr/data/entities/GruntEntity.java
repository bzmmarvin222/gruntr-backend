package de.brockhausag.gruntr.data.entities;

import javax.persistence.*;
import java.time.Instant;

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
    @ManyToOne(targetEntity = GruntEntity.class)
    private GruntEntity replyTo;

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

    public GruntEntity getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(GruntEntity replyTo) {
        this.replyTo = replyTo;
    }
    //</editor-fold>
}
