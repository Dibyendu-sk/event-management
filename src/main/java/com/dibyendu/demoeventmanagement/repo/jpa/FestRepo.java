package com.dibyendu.demoeventmanagement.repo.jpa;

import com.dibyendu.demoeventmanagement.models.entity.Fest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Repository
public interface FestRepo extends JpaRepository<Fest,String> {
    @Query("SELECT f.festName,f.createdDate FROM Fest f WHERE f.createdBy=:createdBy")
    List<Map<String, Instant>> fetchFestNameAndCreatedDate(String createdBy);
}
