package de.brockhausag.gruntr.auth;

import de.brockhausag.gruntr.data.dto.CreateUserDto;
import de.brockhausag.gruntr.data.entities.UserEntity;
import de.brockhausag.gruntr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UserEntity create(CreateUserDto userDto, UserRole role) {
        UserEntity entity = new UserEntity();
        entity.setUserName(userDto.getUserName());
        entity.setPasswordHash(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        entity.setRole(role);
        return userRepository.save(entity);
    }
}
