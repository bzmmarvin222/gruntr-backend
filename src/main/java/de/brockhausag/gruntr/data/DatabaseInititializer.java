package de.brockhausag.gruntr.data;

import de.brockhausag.gruntr.auth.GruntrUserDetailsService;
import de.brockhausag.gruntr.auth.UserRole;
import de.brockhausag.gruntr.data.dto.CreateUserDto;
import de.brockhausag.gruntr.data.entities.GruntEntity;
import de.brockhausag.gruntr.data.entities.UserEntity;
import de.brockhausag.gruntr.repositories.GruntRepository;
import de.brockhausag.gruntr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DatabaseInititializer implements ApplicationListener<ContextRefreshedEvent> {

    public static final String TEST_GRUNT_CONTENT = "Hey. This is a test Grunt. It is the #";
    private boolean alreadySetup = false;

    @Autowired
    GruntrUserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GruntRepository gruntRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        alreadySetup = true;
        setupUsers();
        setupGrunts();
    }

    private void setupUsers() {
        CreateUserDto admin = new CreateUserDto();
        admin.setUserName("admin");
        admin.setPassword("admin");
        admin.setMatchingPassword("admin");
        userDetailsService.create(admin, UserRole.ROLE_ADMIN);

        CreateUserDto user = new CreateUserDto();
        user.setUserName("user");
        user.setPassword("user");
        user.setMatchingPassword("user");
        userDetailsService.create(user, UserRole.ROLE_USER);
    }

    private void setupGrunts() {
        UserEntity defaultUser = userRepository.findByUserName("user");

        GruntEntity[] entities = new GruntEntity[100];

        for (int i = 0; i < 100; i++) {
            GruntEntity gruntEntity = new GruntEntity();
            entities[i] = gruntEntity;
            gruntEntity.setAuthor(defaultUser);
            gruntEntity.setContent(TEST_GRUNT_CONTENT + (i + 1));
            gruntEntity.setPostedOn(Instant.now());
            if (i >= 80) {
                GruntEntity toReply = entities[i - 70];
                gruntEntity.setReplyTo(toReply);
                gruntRepository.save(gruntEntity);
                gruntRepository.save(toReply);
            } else {
                gruntRepository.save(gruntEntity);
            }
        }
    }
}