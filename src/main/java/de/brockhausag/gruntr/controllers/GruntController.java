package de.brockhausag.gruntr.controllers;

import de.brockhausag.gruntr.data.dto.GruntDto;
import de.brockhausag.gruntr.data.entities.GruntEntity;
import de.brockhausag.gruntr.repositories.GruntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/grunts", produces = {MediaTypes.HAL_JSON_VALUE})
public class GruntController {

    @Autowired
    GruntRepository gruntRepository;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<GruntDto>> grunts() {
        Collection<GruntEntity> grunts = gruntRepository.findTop50ByPostedOnBefore(Instant.now());
        Collection<GruntDto> gruntDtos = grunts.stream()
                .map(GruntDto::FromEntity)
                .collect(Collectors.toList());

        Resources resources = Resources.wrap(gruntDtos);
        return ResponseEntity.ok(resources);
    }
}
