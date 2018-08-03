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
        GruntEntity postEntity = createFromDto(gruntToPost);
        GruntEntity savedEntity = gruntRepository.save(postEntity);
        return GruntDto.FromEntity(savedEntity);
    }

    public GruntDto reply(Long repliedId, GruntDto reply) throws IllegalArgumentException {
        GruntEntity replyEntity = createFromDto(reply);
        Optional<GruntEntity> replied = gruntRepository.findById(repliedId);
        if (!replied.isPresent()) {
            throw new IllegalArgumentException("Der Wettbewerb exisitiert nicht.");
        }
        replyEntity.setReplyTo(replied.get());
        GruntEntity savedEntity = gruntRepository.save(replyEntity);
        return GruntDto.FromEntity(savedEntity);
    }

    private GruntEntity createFromDto(GruntDto gruntDto) {
        GruntEntity entity = new GruntEntity();
        entity.setContent(gruntDto.getContent());
        entity.setPostedOn(Instant.now());
        entity.setAuthor(userDetailsService.getCurrentUserEntity());
        return entity;
    }
}
