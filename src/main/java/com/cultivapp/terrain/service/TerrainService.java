package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.SeedType;
import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.entity.dto.PageDTO;
import com.cultivapp.terrain.entity.dto.SeedTypeDTO;
import com.cultivapp.terrain.entity.dto.TerrainDTO;
import com.cultivapp.terrain.entity.dto.TerrainRequest;
import com.cultivapp.terrain.exceptions.ResourceNotFoundException;
import com.cultivapp.terrain.repository.SeedTypeRepository;
import com.cultivapp.terrain.repository.TerrainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TerrainService {

    private final TerrainRepository terrainRepository;
    private final SeedTypeRepository seedTypeRepository;

    @Transactional
    public void createTerrainWithSeedTypes(TerrainRequest request) {
        Terrain terrain = new Terrain();
        terrain.setId(request.getId());
        terrain.setName(request.getName());
        terrain.setArea(request.getArea());
        terrain.setSoilType(request.getSoilType());
        terrain.setPhoto(request.getPhoto());
        terrain.setEmail(request.getEmail());
        terrain.setRemainingDays(request.getRemainingDays());
        terrain.setForSale(request.isForSale());
        terrain.setFullName(request.getFullName());

        Set<SeedType> seedTypes = new HashSet<>();
        for (Long seedTypeId : request.getSeedTypeIds()) {
            SeedType seedType = seedTypeRepository.findById(seedTypeId)
                    .orElseThrow(() -> new RuntimeException("SeedType not found!"));
            seedTypes.add(seedType);
        }

        terrain.setSeedTypes(seedTypes);
        terrainRepository.save(terrain);
    }

    public List<TerrainDTO> convertToTerrainDTOs(List<Terrain> terrains) {
        return terrains.stream()
                .map(terrain -> TerrainDTO.builder()
                        .id(terrain.getId())
                        .name(terrain.getName())
                        .area(terrain.getArea())
                        .soilType(terrain.getSoilType())
                        .photo(terrain.getPhoto())
                        .email(terrain.getEmail())
                        .remainingDays(terrain.getRemainingDays())
                        .forSale(terrain.isForSale())
                        .fullName(terrain.getFullName())
                        .seedTypes(terrain.getSeedTypes().stream()
                                .map(seedType -> SeedTypeDTO.builder()
                                        .id(seedType.getId())
                                        .name(seedType.getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public PageDTO<TerrainDTO> convertToPageDTO(Page<Terrain> terrains) {
        List<TerrainDTO> terrainDTOs = convertToTerrainDTOs(terrains.getContent());
        return PageDTO.<TerrainDTO>builder()
                .content(terrainDTOs)
                .pageNumber(terrains.getNumber())
                .pageSize(terrains.getSize())
                .totalElements(terrains.getTotalElements())
                .totalPages(terrains.getTotalPages())
                .last(terrains.isLast())
                .build();
    }

    public boolean deleteTerrain(Long terrainId) {
        Optional<Terrain> terrainOptional = terrainRepository.findById(terrainId);

        if (terrainOptional.isPresent()) {
            terrainRepository.deleteById(terrainId);
            return true; // Eliminación exitosa
        } else {
            return false; // El terreno con el ID especificado no fue encontrado
        }
    }

    @Transactional
    public boolean deleteTerrain(String terrainName) {
        Optional<Terrain> terrainOptional = terrainRepository.findByName(terrainName);

        if (terrainOptional.isPresent()) {
            terrainRepository.deleteByName(terrainName);
            return true; // Eliminación exitosa
        } else {
            return false; // El terreno con el ID especificado no fue encontrado
        }
    }

    @Transactional
    public boolean deleteTerrainsByEmail(String email) {
        // Borra todos los terrenos asociados al email específico
        terrainRepository.deleteByEmail(email);
        return true;
    }


    @Transactional
    public Terrain updateTerrain(Terrain terrain) {
        Optional<Terrain> existingTerrainOptional = terrainRepository.findByName(terrain.getName());

        if (existingTerrainOptional.isPresent()) {
            Terrain existingTerrain = existingTerrainOptional.get();

            existingTerrain.setName(terrain.getName());
            existingTerrain.setArea(terrain.getArea());
            existingTerrain.setSoilType(terrain.getSoilType());
            existingTerrain.setPlantType(terrain.getPlantType());
            existingTerrain.setPhoto(terrain.getPhoto());
            existingTerrain.setEmail(terrain.getEmail());
            existingTerrain.setRemainingDays(terrain.getRemainingDays());
            existingTerrain.setForSale(terrain.getForSale());
            existingTerrain.setFullName(terrain.getFullName());

            terrainRepository.save(existingTerrain); // Save the updated entity
            return existingTerrain;
        } else {
            throw new RuntimeException("Terrain with ID " + terrain.getId() + " not found.");
        }
    }

    public Page<Terrain> findTerrainsForSale(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return terrainRepository.findAllByForSaleTrue(pageable);
    }

    public Page<Terrain> getMyTerrains(int page, int size, String email){
        Pageable pageable = PageRequest.of(page, size);
        return terrainRepository.findAllByEmail(email, pageable);
    }

    public Terrain getTerrainById(Long id) {
        return terrainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Terreno no encontrado para este id: " + id));
    }

    public Terrain updateTerrain(Long id, Terrain terrainDetails) {
        Terrain terrain = terrainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Terreno no encontrado para este id: " + id));
        terrain.setName(terrainDetails.getName());
        terrain.setArea(terrainDetails.getArea());
        terrain.setSoilType(terrainDetails.getSoilType());
        terrain.setPhoto(terrainDetails.getPhoto());
        terrain.setEmail(terrainDetails.getEmail());
        terrain.setRemainingDays(terrainDetails.getRemainingDays());
        terrain.setForSale(terrainDetails.getForSale());
        terrain.setFullName(terrainDetails.getFullName());

        terrain.getSeedTypes().clear();
        terrain.getSeedTypes().addAll(terrainDetails.getSeedTypes());
        return terrainRepository.save(terrain);
    }

}
