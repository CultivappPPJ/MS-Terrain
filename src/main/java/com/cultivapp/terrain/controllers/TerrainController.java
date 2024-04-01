package com.cultivapp.terrain.controllers;

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
    public ResponseEntity<Page<TerrainDTO>> getAllTerrains(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<TerrainDTO> terrains = terrainService.getAllTerrains(page, size);
        return ResponseEntity.ok(terrains);
    }

    @GetMapping("/crud/terrains")
    public ResponseEntity<Page<TerrainDTO>> getMyTerrains(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam String email) {
        Page<TerrainDTO> terrains = terrainService.getMyTerrains(page, size, email);
        return new ResponseEntity<>(terrains, HttpStatus.OK);
    }

    @GetMapping("/find/terrain/{id}")
    public ResponseEntity<TerrainDTO> getTerrain(@PathVariable Long id){
        TerrainDTO terrainDTO = terrainService.getTerrain(id);
        return new ResponseEntity<>(terrainDTO, HttpStatus.OK);
    }

    @PutMapping("/crud/update/terrain/{id}")
    public ResponseEntity<String> updateTerrain(@PathVariable Long id, @RequestBody TerrainRequest terrainRequest){
        try {
            terrainService.updateTerrain(id, terrainRequest);
            return ResponseEntity.ok("Terreno actualizado con éxito!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falla al actualizar el terreno: " + ex.getMessage());
        }
    }
}
