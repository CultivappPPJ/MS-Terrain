package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.TerrainSeedType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerrainSeedTypeRepository extends JpaRepository<TerrainSeedType, Long> {
}
