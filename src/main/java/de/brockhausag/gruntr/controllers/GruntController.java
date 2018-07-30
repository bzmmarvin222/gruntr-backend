package de.brockhausag.gruntr.controllers;

import de.brockhausag.gruntr.data.dto.GruntDto;
import de.brockhausag.gruntr.data.entities.GruntEntity;
import de.brockhausag.gruntr.repositories.GruntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/grunts", produces = {MediaTypes.HAL_JSON_VALUE})
public class GruntController {

    public static final String GRUNT_ID = "gruntId";

    //TODO: service, too much logic in controller methods
    @Autowired
    GruntRepository gruntRepository;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<GruntDto>> grunts(@RequestParam(value = "before", required = false) Instant before) {
        Collection<GruntEntity> grunts;
        if (before == null) {
            grunts = gruntRepository.findFirst10ByReplyToIsNullOrderByPostedOnDesc();
        } else {
            grunts = gruntRepository.findFirst10ByReplyToIsNullAndPostedOnBeforeOrderByPostedOnDesc(before);
        }

        Collection<GruntDto> gruntDtos = grunts.stream()
                .map(GruntDto::FromEntity)
                .collect(Collectors.toList());

        Resources resources = Resources.wrap(gruntDtos);
        return ResponseEntity.ok(resources);
    }

    @RequestMapping(path = "/{" + GRUNT_ID + "}" ,method = RequestMethod.GET)
    public HttpEntity<GruntDto> grunt(@PathVariable(GRUNT_ID) Long gruntId) {
        Optional<GruntEntity> optionalEntity = gruntRepository.findById(gruntId);
        return optionalEntity
                .map(gruntEntity -> ResponseEntity.ok(GruntDto.FromEntity(gruntEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(path = "/{" + GRUNT_ID + "}/replies" ,method = RequestMethod.GET)
    public HttpEntity<Resources<GruntDto>> replies(@PathVariable(GRUNT_ID) Long gruntId) {
        Collection<GruntEntity> grunts;
        grunts = gruntRepository.findByReplyToId(gruntId);

        Collection<GruntDto> gruntDtos = grunts.stream()
                .map(GruntDto::FromEntity)
                .collect(Collectors.toList());

        Resources resources = Resources.wrap(gruntDtos);
        return ResponseEntity.ok(resources);
    }
}
