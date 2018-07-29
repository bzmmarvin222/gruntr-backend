package de.brockhausag.gruntr.repositories;

import de.brockhausag.gruntr.data.entities.GruntEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Collection;

public interface GruntRepository extends CrudRepository<GruntEntity, Long> {
    Collection<GruntEntity> findFirst10ByIdBeforeOrderByIdDesc(Long id);
    Collection<GruntEntity> findFirst10ByOrderByIdDesc();
}
