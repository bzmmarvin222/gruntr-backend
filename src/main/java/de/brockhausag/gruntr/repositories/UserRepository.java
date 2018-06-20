package de.brockhausag.gruntr.repositories;

import de.brockhausag.gruntr.data.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName(String userName);
}
