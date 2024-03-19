package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.Crop;
import com.cultivapp.terrain.entity.SeedType;
import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.entity.dto.CropRequest;
import com.cultivapp.terrain.exceptions.ResourceNotFoundException;
import com.cultivapp.terrain.repository.CropRepository;
import com.cultivapp.terrain.repository.SeedTypeRepository;
import com.cultivapp.terrain.repository.TerrainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CropService {
    private final CropRepository cropRepository;
    private final SeedTypeRepository seedTypeRepository;
    private final TerrainRepository terrainRepository;

    @Transactional
    public void createCrop(CropRequest cropRequest) {
        SeedType seedType = seedTypeRepository.findById(cropRequest.getSeedTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("SeedType not found with id " + cropRequest.getSeedTypeId()));

        Terrain terrain = terrainRepository.findById(cropRequest.getTerrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Terrain not found with id " + cropRequest.getTerrainId()));

        Crop crop = Crop.builder()
                .seedType(seedType)
                .area(cropRequest.getArea())
                .photo(cropRequest.getPhoto())
                .harvestDate(cropRequest.getHarvestDate())
                .forSale(cropRequest.isForSale())
                .terrain(terrain)
                .build();

        cropRepository.save(crop);
    }

}