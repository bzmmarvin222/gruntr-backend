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
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/grunts", produces = {MediaTypes.HAL_JSON_VALUE})
public class GruntController {

    public static final String GRUNT_ID = "gruntId";

    //TODO: service, too much logik in controller methods
    @Autowired
    GruntRepository gruntRepository;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<GruntDto>> grunts(@RequestParam(value = "before", required = false) Long beforeId) {
        Collection<GruntEntity> grunts;
        if (beforeId == null) {
            grunts = gruntRepository.findFirst10ByOrderByIdDesc();
        } else {
            grunts = gruntRepository.findFirst10ByIdBeforeOrderByIdDesc(beforeId);
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
        Optional<GruntEntity> optionalEntity = gruntRepository.findById(gruntId);
        if (optionalEntity.isPresent()) {
            Collection<GruntDto> replies = optionalEntity.get().getReplies().stream()
                    .map(GruntDto::FromEntity)
                    .collect(Collectors.toList());
            Resources resources = Resources.wrap(replies);
            return ResponseEntity.ok(resources);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
