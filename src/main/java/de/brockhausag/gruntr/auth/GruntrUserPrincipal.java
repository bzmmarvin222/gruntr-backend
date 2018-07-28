package de.brockhausag.gruntr.auth;

import de.brockhausag.gruntr.data.dto.UserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class GruntrUserPrincipal implements UserDetails {

    private UserEntity userEntity;

    public GruntrUserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setRole(userEntity.getRole());
        userDto.setUserName(userEntity.getUserName());
        userDto.setUserId(userEntity.getId());
        return userDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole().toString());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return userEntity.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
