package com.web.dev.repository;

import com.web.dev.entity.SportLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SportLevelRepository extends JpaRepository<SportLevel, Integer> {
    Optional<SportLevel> findByUserId(Integer userId);
}
