package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.repository.TerrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerrainService {

    private final TerrainRepository terrainRepository;

    public Terrain createTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    public List<Terrain> findAllTerrains() {
        return terrainRepository.findAll();
    }
}
