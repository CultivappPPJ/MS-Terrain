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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
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
        terrain.setEnabled(true);

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

    public Page<TerrainDTO> getMyTerrains(int page, int size, String email){
        Pageable pageable = PageRequest.of(page, size);
        Page<Terrain> terrainPage = terrainRepository.findAllByEmail(email, pageable);
        return terrainPage.map(this::convertToDTO);
    }

    public TerrainDTO getTerrain(Long id) {
        return terrainRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Terrain not found"));
    }

    @Transactional
    public void updateTerrain(Long id, TerrainRequest request) {
        Terrain terrain = terrainRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Terrain not found"));

        terrain.setName(request.getName());
        terrain.setArea(request.getArea());
        terrain.setSoilType(request.getSoilType());
        terrain.setPhoto(request.getPhoto());
        terrain.setLocation(request.getLocation());
        terrain.setEmail(request.getEmail());
        terrain.setFullName(request.getFullName());
        terrain.setEnabled(true);

        terrainRepository.save(terrain);
    }
}
