package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.Crop;
import com.cultivapp.terrain.entity.SeedType;
import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.entity.dto.CropDTO;
import com.cultivapp.terrain.entity.dto.CropRequest;
import com.cultivapp.terrain.entity.dto.SeedTypeDTO;
import com.cultivapp.terrain.exceptions.ResourceNotFoundException;
import com.cultivapp.terrain.repository.CropRepository;
import com.cultivapp.terrain.repository.SeedTypeRepository;
import com.cultivapp.terrain.repository.TerrainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .enabled(true)
                .build();

        cropRepository.save(crop);
    }

    @Transactional
    public void updateCrop(Long id, CropRequest request) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Crop not found"));

        SeedType seedType = seedTypeRepository.findById(request.getSeedTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("SeedType not found with id " + request.getSeedTypeId()));

        Terrain terrain = terrainRepository.findById(request.getTerrainId())
                .orElseThrow(() -> new ResourceNotFoundException("Terrain not found with id " + request.getTerrainId()));

        crop.setSeedType(seedType);
        crop.setArea(request.getArea());
        crop.setPhoto(request.getPhoto());
        crop.setHarvestDate(request.getHarvestDate());
        crop.setForSale(request.isForSale());
        crop.setTerrain(terrain);
        crop.setEnabled(true);

        cropRepository.save(crop);
    }

    @Transactional
    public void deleteCropById(Long id) {
        if (!cropRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Crop not found with id: " + id);
        }
        cropRepository.deleteById(id);
    }

    private CropDTO convertToDTO(Crop crop) {
        SeedTypeDTO seedTypeDTO = convertSeedTypeToDTO(crop.getSeedType());

        return CropDTO.builder()
                .id(crop.getId())
                .seedType(seedTypeDTO)
                .area(crop.getArea())
                .photo(crop.getPhoto())
                .harvestDate(crop.getHarvestDate())
                .forSale(crop.isForSale())
                .terrainId(crop.getTerrain().getId())
                .build();
    }

    private SeedTypeDTO convertSeedTypeToDTO(SeedType seedType) {
        return SeedTypeDTO.builder()
                .id(seedType.getId())
                .name(seedType.getName())
                .build();
    }


    public CropDTO getCropById(Long id) {
        return cropRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Crop not found"));
    }

}