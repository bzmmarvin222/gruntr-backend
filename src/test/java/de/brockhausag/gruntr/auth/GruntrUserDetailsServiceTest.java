package de.brockhausag.gruntr.auth;

import de.brockhausag.gruntr.data.dto.CreateUserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import de.brockhausag.gruntr.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = {GruntrUserDetailsService.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class GruntrUserDetailsServiceTest {

    private static final String USER_NAME = "TestUser";
    private static final String USER_PASSWORD = "Password";
    private static final UserRole DEFAULT_USER_ROLE = UserRole.ROLE_USER;

    private Map<String, UserEntity> mockUserDb = new HashMap<>();
    @MockBean
    UserRepository userRepository;

    @Autowired
    GruntrUserDetailsService userDetailsService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CreateUserDto getCreateUserDto() {
        CreateUserDto dto = new CreateUserDto();
        dto.setUserName(USER_NAME);
        dto.setPassword(USER_PASSWORD);
        dto.setMatchingPassword(USER_PASSWORD);
        return dto;
    }

    @Before
    public void setupMock() {
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity entity = (UserEntity) invocation.getArguments()[0];
            mockUserDb.put(entity.getUserName(), entity);
            return entity;
        });

        Mockito.when(userRepository.findByUserName(Mockito.any(String.class))).thenAnswer(invocation -> {
            String name = (String) invocation.getArguments()[0];
            return mockUserDb.get(name);
        });
    }

    @Test
    public void loadUserByUsernameSuccess() {
        CreateUserDto dto = getCreateUserDto();

        userDetailsService.create(dto, DEFAULT_USER_ROLE);

        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_NAME);
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(USER_NAME, userDetails.getUsername());
    }

    @Test
    public void loadByUsernameNotFound() throws UsernameNotFoundException {
        expectedException.expect(UsernameNotFoundException.class);
        userDetailsService.loadUserByUsername("TotalBullshitNotExisting");
    }

    @Test
    public void create() {
        CreateUserDto dto = getCreateUserDto();

        userDetailsService.create(dto, DEFAULT_USER_ROLE);

        UserEntity entity = userRepository.findByUserName(USER_NAME);
        Assert.assertNotNull(entity);
        Assert.assertEquals(USER_NAME, entity.getUserName());
    }

    @Test
    public void createHashesPassword() {
        CreateUserDto dto = getCreateUserDto();

        userDetailsService.create(dto, DEFAULT_USER_ROLE);

        UserEntity entity = userRepository.findByUserName(USER_NAME);
        Assert.assertNotEquals(USER_PASSWORD, entity.getPasswordHash());
    }

    @Test
    public void createSetsDefaultUserRole() {
        CreateUserDto dto = getCreateUserDto();

        userDetailsService.create(dto, DEFAULT_USER_ROLE);

        UserEntity entity = userRepository.findByUserName(USER_NAME);
        Assert.assertEquals(DEFAULT_USER_ROLE, entity.getRole());
    }

    @Test
    public void createSetsAdminRole() {
        CreateUserDto dto = getCreateUserDto();

        userDetailsService.create(dto, UserRole.ROLE_ADMIN);

        UserEntity entity = userRepository.findByUserName(USER_NAME);
        Assert.assertEquals(UserRole.ROLE_ADMIN, entity.getRole());
    }
}