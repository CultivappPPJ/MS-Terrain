package com.cultivapp.terrain.controllers;

import com.cultivapp.terrain.entity.SeedType;
import com.cultivapp.terrain.entity.dto.SeedTypeDTO;
import com.cultivapp.terrain.service.SeedTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seed-types")
@RequiredArgsConstructor
public class PlantTypeController {

    private final SeedTypeService seedTypeService;

    @GetMapping
    public ResponseEntity<List<SeedTypeDTO>> getAllSeedTypes() {
        List<SeedType> seedTypes = seedTypeService.getAllSeedTypes();
        List<SeedTypeDTO> seedTypeDTOS = seedTypes.stream()
                .map(pt -> new SeedTypeDTO(pt.getId(), pt.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(seedTypeDTOS);
    }

}