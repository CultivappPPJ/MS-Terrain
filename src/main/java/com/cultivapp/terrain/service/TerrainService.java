package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.exceptions.ResourceNotFoundException;
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

    public void deleteTerrain(Long id) {
        terrainRepository.deleteById(id);
    }

    public Terrain getTerrainById(Long id) {
        return terrainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Terrain not found for this id: " + id));
    }

    public Terrain updateTerrain(Long id, Terrain terrainDetails) {
        Terrain terrain = terrainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Terreno no encontrado para este id: " + id));
        terrain.setArea(terrainDetails.getArea());
        terrain.setSoilType(terrainDetails.getSoilType());
        terrain.setPlantType(terrainDetails.getPlantType());
        terrain.setPhoto(terrainDetails.getPhoto());
        terrain.setEmail(terrainDetails.getEmail());
        terrain.setRemainingDays(terrainDetails.getRemainingDays());
        terrain.setForSale(terrainDetails.isForSale());
        terrain.setFullName(terrainDetails.getFullName());

        return terrainRepository.save(terrain);
    }

}
