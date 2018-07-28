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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = {MediaTypes.HAL_JSON_VALUE})
public class UserController {
    @Autowired
    GruntrUserDetailsService userDetailsService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public HttpEntity<UserDto> user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        GruntrUserPrincipal userDetails = (GruntrUserPrincipal) userDetailsService.loadUserByUsername(userName);
        UserDto result  = userDetails.getUserDto();
        return ResponseEntity.ok(result);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/noadmin", method = RequestMethod.GET)
    public HttpEntity<UserEntity> noAdminTestMethod() {
        return ResponseEntity.ok(new UserEntity());
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public HttpEntity<UserEntity> onlyAdminTestMethod() {
        return ResponseEntity.ok(new UserEntity());
    }
}
