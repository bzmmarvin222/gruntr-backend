package de.brockhausag.gruntr.data.dto;

public class CreateUserDto {
    private String userName;
    private String password;
    private String matchingPassword;

    //<editor-fold desc="getters and setters">
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
    //</editor-fold>
}
