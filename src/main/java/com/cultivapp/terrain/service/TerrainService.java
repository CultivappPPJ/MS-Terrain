package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.SeedType;
import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.entity.TerrainSeedType;
import com.cultivapp.terrain.entity.dto.PageDTO;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        terrain.setEmail(terrainRequest.getEmail());
        terrain.setRemainingDays(terrainRequest.getRemainingDays());
        terrain.setForSale(terrainRequest.isForSale());
        terrain.setFullName(terrainRequest.getFullName());
        terrain.setLocation(terrainRequest.getLocation());
        terrain.setEnabled(true);

        Set<TerrainSeedType> terrainSeedTypes = new HashSet<>();
        for (Long seedTypeId : terrainRequest.getSeedTypeIds()) {
            SeedType seedType = seedTypeRepository.findById(seedTypeId)
                    .orElseThrow(() -> new RuntimeException("SeedType not found: " + seedTypeId));
            TerrainSeedType terrainSeedType = new TerrainSeedType();
            terrainSeedType.setTerrain(terrain);
            terrainSeedType.setSeedType(seedType);
            terrainSeedTypes.add(terrainSeedType);
        }
        terrain.setSeedTypes(terrainSeedTypes);

        terrainRepository.save(terrain);
    }

    public PageDTO<TerrainDTO> convertToPageDTO(Page<Terrain> terrains) {
        List<TerrainDTO> terrainDTOList = terrains.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageDTO<>(
                terrainDTOList,
                terrains.getNumber(),
                terrains.getSize(),
                terrains.getTotalElements(),
                terrains.getTotalPages(),
                terrains.isLast());
    }

    private TerrainDTO convertToDTO(Terrain terrain) {
        List<SeedTypeDTO> seedTypeDTOList = terrain.getSeedTypes().stream()
                .map(terrainSeedType -> {
                    SeedType seedType = terrainSeedType.getSeedType();
                    return new SeedTypeDTO(seedType.getId(), seedType.getName());
                })
                .collect(Collectors.toList());

        return new TerrainDTO(
                terrain.getId(),
                terrain.getName(),
                terrain.getArea(),
                terrain.getSoilType(),
                terrain.getPhoto(),
                terrain.getEmail(),
                terrain.getRemainingDays(),
                terrain.isForSale(),
                terrain.getFullName(),
                seedTypeDTOList,
                terrain.getLocation());
    }

    @Transactional
    public void deleteTerrainById(Long id) {
        Optional<Terrain> optionalTerrain = terrainRepository.findById(id);
        optionalTerrain.ifPresent(terrain -> {
            terrain.setEnabled(false);
            terrainRepository.save(terrain);
        });
    }

    public boolean deleteTerrainsByEmail(String email) {
        List<Terrain> terrainList = terrainRepository.findAllByEmail(email);
        terrainList.forEach(terrain -> terrain.setEnabled(false));
        terrainRepository.saveAll(terrainList);
        return true;
    }


    public Page<Terrain> findTerrainsForSale(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return terrainRepository.findAllByForSaleTrue(pageable);
    }

    public Page<Terrain> getMyTerrains(int page, int size, String email){
        Pageable pageable = PageRequest.of(page, size);
        return terrainRepository.findAllByEmail(email, pageable);
    }

    public TerrainDTO getTerrainById(Long id) {
        Terrain terrain = terrainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Terrain not found for this id :: " + id));
        return convertToDTO(terrain);

    }

    public TerrainDTO updateTerrain(Long id, TerrainDTO terrainDTO) {
        Terrain terrain = terrainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Terrain not found for this id :: " + id));
        terrain.setId(terrainDTO.getId());
        terrain.setName(terrainDTO.getName());
        terrain.setArea(terrainDTO.getArea());
        terrain.setSoilType(terrainDTO.getSoilType());
        terrain.setPhoto(terrainDTO.getPhoto());
        terrain.setEmail(terrainDTO.getEmail());
        terrain.setRemainingDays(terrainDTO.getRemainingDays());
        terrain.setForSale(terrainDTO.isForSale());
        terrain.setFullName(terrainDTO.getFullName());
        terrain.setLocation(terrainDTO.getLocation());

        Terrain updatedTerrain = terrainRepository.save(terrain);
        return convertToDTO(updatedTerrain);
    }

}
