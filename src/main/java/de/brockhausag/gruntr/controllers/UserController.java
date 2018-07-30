package de.brockhausag.gruntr.controllers;

import de.brockhausag.gruntr.auth.GruntrUserDetailsService;
import de.brockhausag.gruntr.auth.GruntrUserPrincipal;
import de.brockhausag.gruntr.data.dto.UserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/api/user", produces = {MediaTypes.HAL_JSON_VALUE})
public class UserController {
    public static final String USER_ID = "userId";

    @Autowired
    GruntrUserDetailsService userDetailsService;

    //TODO: method argument provider or resolver?, passes user to method
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<UserDto> user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        GruntrUserPrincipal principal = (GruntrUserPrincipal) userDetailsService.loadUserByUsername(userName);
        UserDto result  = principal.getUserDto();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/{" + USER_ID + "}", method = RequestMethod.GET)
    public HttpEntity<UserDto> getUser(@PathVariable(USER_ID) Long id) {
        GruntrUserPrincipal principal;
        try {
             principal = userDetailsService.loadById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        UserDto result  = principal.getUserDto();
        return ResponseEntity.ok(result);
    }
}
