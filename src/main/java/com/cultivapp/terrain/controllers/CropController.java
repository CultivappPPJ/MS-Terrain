package com.cultivapp.terrain.controllers;

import com.cultivapp.terrain.entity.dto.CropRequest;
import com.cultivapp.terrain.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crop")
@CrossOrigin("http://localhost:5173/")
public class CropController {
    private final CropService cropService;

    @PostMapping("/add")
    public ResponseEntity<String> createCrop(@RequestBody CropRequest cropRequest) {
        try {
            cropService.createCrop(cropRequest);
            return ResponseEntity.ok("Cultivo agregado con exito!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falla al agregar el cultivo: " + ex.getMessage());
        }
    }
}