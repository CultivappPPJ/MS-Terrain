package com.cultivapp.terrain.controllers;

import com.cultivapp.terrain.entity.Terrain;
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
    public ResponseEntity<Terrain> createTerrain(@RequestBody Terrain terrain) {
        Terrain createdTerrain = terrainService.createTerrain(terrain);
        return new ResponseEntity<>(createdTerrain, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Terrain>> getTerrainsForSale(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<Terrain> terrains = terrainService.findTerrainsForSale(page, size);
        return new ResponseEntity<>(terrains, HttpStatus.OK);
    }

    @GetMapping("/crud/terrains")
    public ResponseEntity<Page<Terrain>> getMyTerrains(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam String email) {
        Page<Terrain> terrains = terrainService.getMyTerrains(page, size, email);
        return new ResponseEntity<>(terrains, HttpStatus.OK);
    }

    @DeleteMapping("/crud/delete/{id}")
    public ResponseEntity<Void> deleteTerrain(@PathVariable Long id) {
        terrainService.deleteTerrain(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/crud/{id}")
    public ResponseEntity<Terrain> getTerrainById(@PathVariable Long id) {
        Terrain terrain = terrainService.getTerrainById(id);
        return new ResponseEntity<>(terrain, HttpStatus.OK);
    }


    @PutMapping("/crud/update/{id}")
    public ResponseEntity<Terrain> updateTerrain(@PathVariable Long id, @RequestBody Terrain terrainDetails) {
        Terrain updatedTerrain = terrainService.updateTerrain(id, terrainDetails);
        return ResponseEntity.ok(updatedTerrain);
    }

}
