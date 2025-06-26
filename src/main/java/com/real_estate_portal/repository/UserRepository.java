package com.real_estate_portal.repository;

// src/main/java/com/realestate/portal/repository/UserRepository.java

import com.real_estate_portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
