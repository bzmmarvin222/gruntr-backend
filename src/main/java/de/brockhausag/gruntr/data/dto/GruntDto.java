package de.brockhausag.gruntr.data.dto;

import de.brockhausag.gruntr.controllers.GruntController;
import de.brockhausag.gruntr.controllers.UserController;
import de.brockhausag.gruntr.data.entities.GruntEntity;
import org.springframework.hateoas.ResourceSupport;

import java.time.Instant;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class GruntDto extends ResourceSupport {
    public static final String AUTHOR_REL = "author";
    public static final String REPLIES_REL = "replies";

    private Instant postedOn;
    private String content;

    //<editor-fold desc="getters and setters">
    public Instant getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Instant postedOn) {
        this.postedOn = postedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    //</editor-fold>

    public static GruntDto FromEntity(GruntEntity entity) {
        GruntDto result = new GruntDto();
        result.setContent(entity.getContent());
        result.setPostedOn(entity.getPostedOn());
        result.add(linkTo(methodOn(UserController.class).getUser(entity.getAuthor().getId())).withRel(AUTHOR_REL));
        result.add(linkTo(methodOn(GruntController.class).grunt(entity.getId())).withSelfRel());
        result.add(linkTo(methodOn(GruntController.class).replies(entity.getId())).withRel(REPLIES_REL));
        return result;
    }
}
