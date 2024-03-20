package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.Terrain;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {

    @Transactional
    @Query("SELECT t FROM Terrain t WHERE t.forSale = true AND t.enabled = true")
    Page<Terrain> findAllByForSaleTrue(Pageable pageable);

    @Transactional
    @Query("SELECT t FROM Terrain t WHERE t.email = :email AND t.enabled = true")
    Page<Terrain> findAllByEmail(@Param("email") String email, Pageable pageable);

    @Transactional
    @Query("SELECT t FROM Terrain t WHERE t.email = :email AND t.enabled = true")
    List<Terrain> findAllByEmail(@Param("email") String email);

    @Transactional
    @Query("SELECT t FROM Terrain t WHERE t.id = :id AND t.enabled = true")
    Optional<Terrain> findById(@Param("id") Long id);
}
