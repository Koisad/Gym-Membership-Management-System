package com.sii.gym.repository;

import com.sii.gym.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
    boolean existsByName(String name);
}