package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {
}
