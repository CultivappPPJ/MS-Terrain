package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.Crop;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop, Long> {

    @Transactional
    @Query("SELECT t FROM Crop t WHERE t.id = :id AND t.enabled = true")
    Optional<Crop> findById(@Param("id") Long id);

    @Query("SELECT SUM(c.area) FROM Crop c WHERE c.terrain.id = :terrainId AND c.id <> :excludeCropId")
    Optional<Long> sumAreaByTerrainIdExcludingCropId(@Param("terrainId") Long terrainId, @Param("excludeCropId") Long excludeCropId);

    @Query("SELECT SUM(c.area) FROM Crop c WHERE c.terrain.id = :terrainId")
    Optional<Long> sumAreaByTerrainId(@Param("terrainId") Long terrainId);
}