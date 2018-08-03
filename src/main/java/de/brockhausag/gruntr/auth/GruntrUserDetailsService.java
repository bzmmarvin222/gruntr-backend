package de.brockhausag.gruntr.auth;

import de.brockhausag.gruntr.data.dto.CreateUserDto;
import de.brockhausag.gruntr.data.dto.UserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import de.brockhausag.gruntr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GruntrUserDetailsService implements UserDetailsService {

     @Autowired
     private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserName(username);

        if (userEntity == null) {
            throw  new UsernameNotFoundException(username);
        } else {
            return new GruntrUserPrincipal(userEntity);
        }
    }

    public GruntrUserPrincipal loadById(Long id) throws EntityNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (!optionalUserEntity.isPresent()) {
            throw new EntityNotFoundException(String.format("User with id %d does not exist.", id));
        } else {
            return new GruntrUserPrincipal(optionalUserEntity.get());
        }
    }

    public UserEntity create(CreateUserDto userDto, UserRole role) {
        UserEntity entity = new UserEntity();
        entity.setUserName(userDto.getUserName());
        entity.setPasswordHash(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        entity.setRole(role);
        return userRepository.save(entity);
    }

    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        GruntrUserPrincipal principal = (GruntrUserPrincipal) loadUserByUsername(userName);
        return principal.getUserDto();
    }
}
