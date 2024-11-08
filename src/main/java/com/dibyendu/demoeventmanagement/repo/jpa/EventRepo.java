package com.dibyendu.demoeventmanagement.repo.jpa;

import com.dibyendu.demoeventmanagement.models.FestDto;
import com.dibyendu.demoeventmanagement.models.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Events,String> {
//    @Query("SELECT COUNT(e) > 0FROM Events e WHERE =:festId AND e.eventName=:eventName")
//    boolean fetchEventDetailsByEventNameAndFestId(String eventName,String festId);
//@Query("SELECT new com.dibyendu.demoeventmanagement.models.EventDto(f.id,f.festName,f.createdDate) FROM Events e WHERE e= :createdBy")
//List<FestDto> fetchFestNameAndCreatedDate(String createdBy);
}
