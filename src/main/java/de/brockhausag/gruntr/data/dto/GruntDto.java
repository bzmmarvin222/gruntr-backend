package de.brockhausag.gruntr.data.dto;

import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;

public class GruntDto extends ResourceSupport {
    private LocalDate postedOn;
    private String content;
}
