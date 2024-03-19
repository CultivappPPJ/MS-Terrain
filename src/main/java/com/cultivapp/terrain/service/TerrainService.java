package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.entity.dto.CropDTO;
import com.cultivapp.terrain.entity.dto.SeedTypeDTO;
import com.cultivapp.terrain.entity.dto.TerrainDTO;
import com.cultivapp.terrain.entity.dto.TerrainRequest;
import com.cultivapp.terrain.repository.SeedTypeRepository;
import com.cultivapp.terrain.repository.TerrainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TerrainService {

    private final TerrainRepository terrainRepository;
    private final SeedTypeRepository seedTypeRepository;

    @Transactional
    public void createTerrain(TerrainRequest terrainRequest) {
        Terrain terrain = new Terrain();
        terrain.setName(terrainRequest.getName());
        terrain.setArea(terrainRequest.getArea());
        terrain.setSoilType(terrainRequest.getSoilType());
        terrain.setPhoto(terrainRequest.getPhoto());
        terrain.setLocation(terrainRequest.getLocation());
        terrain.setEmail(terrainRequest.getEmail());
        terrain.setFullName(terrainRequest.getFullName());

        terrainRepository.save(terrain);
    }

    public Page<TerrainDTO> getAllTerrains(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Terrain> terrainPage = terrainRepository.findAll(pageable);

        return terrainPage.map(this::convertToDTO);
    }

    private TerrainDTO convertToDTO(Terrain terrain) {
        List<CropDTO> cropDTOList = terrain.getCrops().stream()
                .map(crop -> CropDTO.builder()
                        .id(crop.getId())
                        .seedType(SeedTypeDTO.builder()
                                .id(crop.getSeedType().getId())
                                .name(crop.getSeedType().getName())
                                .build())
                        .area(crop.getArea())
                        .photo(crop.getPhoto())
                        .harvestDate(crop.getHarvestDate())
                        .forSale(crop.isForSale())
                        .build())
                .collect(Collectors.toList());

        return TerrainDTO.builder()
                .id(terrain.getId())
                .name(terrain.getName())
                .area(terrain.getArea())
                .soilType(terrain.getSoilType())
                .photo(terrain.getPhoto())
                .location(terrain.getLocation())
                .email(terrain.getEmail())
                .fullName(terrain.getFullName())
                .crops(cropDTOList)
                .build();
    }

    @Transactional
    public boolean deleteTerrainsByEmail(String email) {
        // Borra todos los terrenos asociados al email específico
        terrainRepository.deleteByEmail(email);
        return true;
    }

    public Page<TerrainDTO> getMyTerrains(int page, int size, String email){
        Pageable pageable = PageRequest.of(page, size);
        Page<Terrain> terrainPage = terrainRepository.findAllByEmail(email, pageable);
        return terrainPage.map(this::convertToDTO);
    }

    public void deleteTerrainById(Long id) {
        terrainRepository.deleteById(id);
    }
}
