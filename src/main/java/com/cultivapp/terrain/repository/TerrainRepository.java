package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.Terrain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {

    Page<Terrain> findAllByEmail(String email, Pageable pageable);

    Optional<Terrain> findByName(String name);

    void deleteByName(String name);

    void deleteByEmail(String email);
}
