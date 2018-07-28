package de.brockhausag.gruntr.controllers;

import de.brockhausag.gruntr.auth.GruntrUserDetailsService;
import de.brockhausag.gruntr.auth.UserRole;
import de.brockhausag.gruntr.data.dto.CreateUserDto;
import de.brockhausag.gruntr.data.dto.UserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/register", produces = { MediaTypes.HAL_JSON_VALUE })
public class RegistrationController {
    @Autowired
    GruntrUserDetailsService userDetailsService;

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<UserDto> create(@RequestBody CreateUserDto userDto) {
        UserEntity created = userDetailsService.create(userDto, UserRole.ROLE_USER);
        UserDto dto = UserDto.FromEntity(created);
        return ResponseEntity.ok(dto);
    }
}
