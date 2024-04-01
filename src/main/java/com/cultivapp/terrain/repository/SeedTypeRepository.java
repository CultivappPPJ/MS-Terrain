package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.SeedType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedTypeRepository extends JpaRepository<SeedType, Long> {
}