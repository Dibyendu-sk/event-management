package com.dibyendu.demoeventmanagement.repo;

import com.dibyendu.demoeventmanagement.models.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserInfo,String> {
    Optional<UserInfo> findByEmail(String email);
    boolean existsByEmail(String email);
}
