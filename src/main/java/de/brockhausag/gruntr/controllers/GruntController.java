package de.brockhausag.gruntr.controllers;

import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/grunts", produces = {MediaTypes.HAL_JSON_VALUE})
public class GruntController {
}
