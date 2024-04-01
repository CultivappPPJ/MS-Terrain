package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.Crop;
import com.cultivapp.terrain.entity.dto.CropDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {

    @Transactional
    @Query("SELECT c FROM Crop c WHERE c.id = :id AND c.enabled = true")
    Optional<Crop> findById(@Param("id") Long id);

    @Transactional
    @Query("SELECT c FROM Crop c WHERE c.forSale = true AND c.enabled = true")
    Page<Crop> findCropsForSale(Pageable pageable);
}