package de.brockhausag.gruntr.controllers;

import de.brockhausag.gruntr.data.dto.GruntDto;
import de.brockhausag.gruntr.repositories.GruntRepository;
import de.brockhausag.gruntr.services.GruntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/grunts", produces = {MediaTypes.HAL_JSON_VALUE})
public class GruntController {

    public static final String GRUNT_ID = "gruntId";

    @Autowired
    GruntRepository gruntRepository;

    @Autowired
    GruntService gruntService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<Resources<GruntDto>> grunts(@RequestParam(value = "before", required = false) Instant before) {
        Collection<GruntDto> gruntDtos = gruntService.getByTimestamp(before);
        Resources resources = Resources.wrap(gruntDtos);
        return ResponseEntity.ok(resources);
    }

    @RequestMapping(path = "/{" + GRUNT_ID + "}" ,method = RequestMethod.GET)
    public HttpEntity<GruntDto> grunt(@PathVariable(GRUNT_ID) Long gruntId) {
        Optional<GruntDto> optionalEntity = gruntService.getById(gruntId);
        return optionalEntity
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(path = "/{" + GRUNT_ID + "}/replies" ,method = RequestMethod.GET)
    public HttpEntity<Resources<GruntDto>> replies(@PathVariable(GRUNT_ID) Long gruntId) {
        Collection<GruntDto> gruntDtos = gruntService.getReplies(gruntId);
        Resources resources = Resources.wrap(gruntDtos);
        return ResponseEntity.ok(resources);
    }

    @RequestMapping(path = "/post", method = RequestMethod.PUT)
    public HttpEntity<GruntDto> post(@RequestBody GruntDto gruntDto) {
        GruntDto posted = gruntService.post(gruntDto);
        URI rel = posted.getId().getTemplate().expand();
        return ResponseEntity.created(rel).body(posted);
    }

    @RequestMapping(path = "/{" + GRUNT_ID + "}/reply", method = RequestMethod.PUT)
    public HttpEntity<GruntDto> reply(@PathVariable(GRUNT_ID) Long repliedId, @RequestBody GruntDto gruntDto) {
        GruntDto posted = null;
        try {
            gruntService.reply(repliedId, gruntDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        URI rel = posted.getId().getTemplate().expand();
        return ResponseEntity.created(rel).body(posted);
    }
}
