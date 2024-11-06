package com.dibyendu.demoeventmanagement.repo.mongo;

import com.dibyendu.demoeventmanagement.models.documents.Participants;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepo extends MongoRepository<Participants,String> {
//    @Aggregation(pipeline = {
//            "{ $match: { festId: ?0, name: ?1, _id: ?2 } }"
//    })
//    Optional<Participants> fetchParticipantDtls(String festId, String name, String reg);
    Optional<Participants> findByFestIdAndNameAndId(String festId, String name, String reg);
}
