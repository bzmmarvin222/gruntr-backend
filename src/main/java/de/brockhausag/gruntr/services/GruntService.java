package de.brockhausag.gruntr.services;

import de.brockhausag.gruntr.auth.GruntrUserDetailsService;
import de.brockhausag.gruntr.data.dto.GruntDto;
import de.brockhausag.gruntr.data.entities.GruntEntity;
import de.brockhausag.gruntr.repositories.GruntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GruntService {

    @Autowired
    GruntRepository gruntRepository;

    @Autowired
    GruntrUserDetailsService userDetailsService;

    public Collection<GruntDto> getByTimestamp(Instant timestamp) {
        Collection<GruntEntity> grunts;
        if (timestamp == null) {
            grunts = gruntRepository.findFirst10ByReplyToIsNullOrderByPostedOnDesc();
        } else {
            grunts = gruntRepository.findFirst10ByReplyToIsNullAndPostedOnBeforeOrderByPostedOnDesc(timestamp);
        }

        return grunts.stream()
                .map(GruntDto::FromEntity)
                .collect(Collectors.toList());
    }

    public Optional<GruntDto> getById(Long id) {
        return gruntRepository.findById(id).map(GruntDto::FromEntity);
    }

    public Collection<GruntDto> getReplies(Long id) {
        Collection<GruntEntity> gruntEntities = gruntRepository.findByReplyToId(id);
        return gruntEntities.stream()
                .map(GruntDto::FromEntity)
                .collect(Collectors.toList());
    }

    public GruntDto post(GruntDto gruntToPost) {
        GruntEntity entity = new GruntEntity();
        entity.setContent(gruntToPost.getContent());
        entity.setPostedOn(Instant.now());
        entity.setAuthor(userDetailsService.getCurrentUserEntity());
        GruntEntity savedEntity = gruntRepository.save(entity);
        return GruntDto.FromEntity(savedEntity);
    }
}
