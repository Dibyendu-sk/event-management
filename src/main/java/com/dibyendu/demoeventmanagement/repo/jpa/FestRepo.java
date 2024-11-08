package com.dibyendu.demoeventmanagement.repo.jpa;

import com.dibyendu.demoeventmanagement.models.FestDto;
import com.dibyendu.demoeventmanagement.models.entity.Events;
import com.dibyendu.demoeventmanagement.models.entity.Fest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface FestRepo extends JpaRepository<Fest,String> {
    @Query("SELECT new com.dibyendu.demoeventmanagement.models.FestDto(f.id,f.festName,f.createdDate) FROM Fest f WHERE f.createdBy= :createdBy")
    List<FestDto>fetchFestNameAndCreatedDate(String createdBy);
    @Query("SELECT f.events FROM Fest f where f.id= :festId")
    Set<Events> fetchEventsFromFestId(@Param("festId")String festId);
}
