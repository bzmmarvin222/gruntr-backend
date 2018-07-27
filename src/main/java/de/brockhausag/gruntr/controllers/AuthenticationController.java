package de.brockhausag.gruntr.controllers;

import de.brockhausag.gruntr.auth.GruntrUserDetailsService;
import de.brockhausag.gruntr.data.dto.CreateUserDto;
import de.brockhausag.gruntr.data.dto.UserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import de.brockhausag.gruntr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GruntrUserDetailsService userDetailsService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<UserDto> helloWorld() {
        UserEntity userEntity = userRepository.findByUserName("Klaus");
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserDto.FromEntity(userEntity));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public UserDto register(@RequestBody CreateUserDto userDto) {
        UserEntity created = userDetailsService.create(userDto);
        return UserDto.FromEntity(created);
    }
}
