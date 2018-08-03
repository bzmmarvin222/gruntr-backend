package de.brockhausag.gruntr.auth;

import de.brockhausag.gruntr.data.dto.UserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GruntrUserPrincipal implements UserDetails {

    private UserEntity userEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public GruntrUserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserDto getUserDto() {
        return UserDto.FromEntity(userEntity);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_USER);

        if (userEntity.getRole() == UserRole.ROLE_ADMIN) {
            roles.add(UserRole.ROLE_ADMIN);
        }

        return roles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.toString()))
                .collect(Collectors.toList());
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
