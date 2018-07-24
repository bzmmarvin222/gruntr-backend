package de.brockhausag.gruntr.controllers;

import de.brockhausag.gruntr.data.entities.UserEntity;
import de.brockhausag.gruntr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public UserEntity helloWorld() {
        UserEntity userEntity = userRepository.findByUserName("Klaus");
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setUserName("TestUser");
            userEntity.setPassword("Test123");
            userRepository.save(userEntity);
        }
        return userEntity;
    }
}
