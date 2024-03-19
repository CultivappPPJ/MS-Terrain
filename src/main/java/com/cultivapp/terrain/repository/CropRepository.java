package com.cultivapp.terrain.repository;

import com.cultivapp.terrain.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long> {
}