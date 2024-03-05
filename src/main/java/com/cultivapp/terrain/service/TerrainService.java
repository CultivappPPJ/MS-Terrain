package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.repository.TerrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TerrainService {

    private final TerrainRepository terrainRepository;

    public Terrain createTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    public Page<Terrain> findTerrainsForSale(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return terrainRepository.findAllByForSaleTrue(pageable);
    }

    public Page<Terrain> getMyTerrains(int page, int size, String email){
        Pageable pageable = PageRequest.of(page, size);
        return terrainRepository.findAllByEmail(email, pageable);
    }
}
