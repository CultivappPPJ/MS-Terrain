package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.Terrain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {

    Page<Terrain> findAllByForSaleTrue(Pageable pageable);

    Page<Terrain> findAllByEmail(String email, Pageable pageable);
}
