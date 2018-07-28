package de.brockhausag.gruntr.auth;

import com.google.common.collect.Iterables;
import de.brockhausag.gruntr.data.entities.UserEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

public class GruntrUserPrincipalTest {

    private static final String USER_PASSWORD_HASH = "Passwort";
    private static final String USER_NAME = "KlausMcTesterson";
    private static final UserRole USER_ROLE = UserRole.DEFAULT_USER;
    private static final long USER_ID = 1337;

    private GruntrUserPrincipal principal;

    @Before
    public void setupPrincipal() {
        UserEntity entity = new UserEntity();
        entity.setPasswordHash(USER_PASSWORD_HASH);
        entity.setRole(USER_ROLE);
        entity.setUserName(USER_NAME);
        entity.setId(USER_ID);
        principal = new GruntrUserPrincipal(entity);
    }

    @Test
    public void getAuthorities() {
        Assert.assertEquals(1, principal.getAuthorities().size());
        GrantedAuthority authority = Iterables.get(principal.getAuthorities(), 0);
        Assert.assertEquals(UserRole.DEFAULT_USER.toString(), authority.getAuthority());
    }

    @Test
    public void getPassword() {
        Assert.assertEquals(USER_PASSWORD_HASH, principal.getPassword());
    }

    @Test
    public void getUsername() {
        Assert.assertEquals(USER_NAME, principal.getUsername());
    }

    @Test
    public void isAccountNonExpired() {
        Assert.assertTrue(principal.isAccountNonExpired());
    }

    @Test
    public void isAccountNonLocked() {
        Assert.assertTrue(principal.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired() {
        Assert.assertTrue(principal.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled() {
        Assert.assertTrue(principal.isEnabled());

    }
}