package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.TerrainSeedType;
import com.cultivapp.terrain.repository.TerrainSeedTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerrainSeedTypeService {

    private final TerrainSeedTypeRepository terrainSeedTypeRepository;

    @Transactional
    public void disableTerrainSeedTypesByTerrainId(Long terrainId) {
        List<TerrainSeedType> terrainSeedTypes = terrainSeedTypeRepository.findAllByTerrainId(terrainId);
        terrainSeedTypes.forEach(terrainSeedType -> {
            terrainSeedType.setEnabled(false);
            terrainSeedTypeRepository.save(terrainSeedType);
        });
    }
}
