package de.brockhausag.gruntr.data.dto;

import de.brockhausag.gruntr.auth.UserRole;
import de.brockhausag.gruntr.data.entities.UserEntity;

public class UserDto {
    private long id;
    private String userName;
    private UserRole role;

    //<editor-fold desc="getters and setters">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        result.setId(entity.getId());
        result.setUserName(entity.getUserName());
        result.setRole(entity.getRole());
        return result;
    }
}
