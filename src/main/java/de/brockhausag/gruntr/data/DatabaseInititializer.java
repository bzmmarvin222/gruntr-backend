package de.brockhausag.gruntr.data;

import de.brockhausag.gruntr.auth.GruntrUserDetailsService;
import de.brockhausag.gruntr.auth.UserRole;
import de.brockhausag.gruntr.data.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInititializer implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    GruntrUserDetailsService userDetailsService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        alreadySetup = true;

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
}