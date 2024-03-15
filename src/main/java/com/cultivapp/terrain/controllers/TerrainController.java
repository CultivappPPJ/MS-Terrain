package com.cultivapp.terrain.controllers;

import com.cultivapp.terrain.entity.Terrain;
import com.cultivapp.terrain.entity.dto.PageDTO;
import com.cultivapp.terrain.entity.dto.TerrainDTO;
import com.cultivapp.terrain.entity.dto.TerrainRequest;
import com.cultivapp.terrain.service.TerrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/terrain")
@CrossOrigin("http://localhost:5173/")
public class TerrainController {
    private final TerrainService terrainService;

    @PostMapping("/crud/create")
    public ResponseEntity<String> createTerrain(@RequestBody TerrainRequest terrainRequest) {
        try {
            terrainService.createTerrain(terrainRequest);
            return ResponseEntity.ok("Terreno creado con exito!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falla al crear el terreno: " + ex.getMessage());
        }
    }

    @DeleteMapping("/crud/delete/{id}")
    public ResponseEntity<Void> deleteTerrainById(@PathVariable Long id) {
        terrainService.deleteTerrainById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/crud/deleteByUser/{email}")
    public ResponseEntity<String> deleteTerrainsByUser(@PathVariable String email) {
        boolean deleted = terrainService.deleteTerrainsByEmail(email);

        if (deleted) {
            return new ResponseEntity<>("Terrenos eliminados exitosamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontraron terrenos del usuario.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<PageDTO<TerrainDTO>> getTerrainsForSale(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<Terrain> terrains = terrainService.findTerrainsForSale(page, size);
        PageDTO<TerrainDTO> pageDTO = terrainService.convertToPageDTO(terrains);
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @GetMapping("/crud/terrains")
    public ResponseEntity<PageDTO<TerrainDTO>> getMyTerrains(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam String email) {
        Page<Terrain> terrains = terrainService.getMyTerrains(page, size, email);
        PageDTO<TerrainDTO> pageDTO = terrainService.convertToPageDTO(terrains);
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @GetMapping("/crud/{id}")
    public ResponseEntity<TerrainDTO> getTerrainById(@PathVariable Long id) {
        TerrainDTO terrainDTO = terrainService.getTerrainById(id);
        return new ResponseEntity<>(terrainDTO, HttpStatus.OK);
    }

    @PutMapping("/crud/update/{id}")
    public ResponseEntity<TerrainDTO> updateTerrain(@PathVariable Long id, @RequestBody TerrainDTO terrainDTO) {
        TerrainDTO updatedTerrainDTO = terrainService.updateTerrain(id, terrainDTO);
        return new ResponseEntity<>(updatedTerrainDTO, HttpStatus.OK);
    }

}
