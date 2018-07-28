package de.brockhausag.gruntr.data.dto;

import de.brockhausag.gruntr.auth.UserRole;
import de.brockhausag.gruntr.data.entities.UserEntity;
import org.springframework.hateoas.ResourceSupport;

public class UserDto extends ResourceSupport {
    private long userId;
    private String userName;
    private UserRole role;

    //<editor-fold desc="getters and setters">
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    //</editor-fold>

    public static UserDto FromEntity(UserEntity entity) {
        UserDto result = new UserDto();
        result.setUserId(entity.getId());
        result.setUserName(entity.getUserName());
        result.setRole(entity.getRole());
        return result;
    }
}
