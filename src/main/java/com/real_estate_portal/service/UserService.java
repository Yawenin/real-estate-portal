package com.real_estate_portal.service;

// src/main/java/com/realestate/portal/service/UserService.java

import com.real_estate_portal.dto.UserRegistrationDto;
import com.real_estate_portal.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
