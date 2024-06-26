package com.cultivapp.terrain.controllers;

import com.cultivapp.terrain.entity.dto.CropDTO;
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

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCrop(@PathVariable Long id, @RequestBody CropRequest cropRequest){
        try {
            cropService.updateCrop(id, cropRequest);
            return ResponseEntity.ok("Cultivo actualizado con éxito!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falla al actualizar el cultivo: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCropById(@PathVariable Long id) {
        cropService.deleteCropById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/crop/{id}")
    public ResponseEntity<CropDTO> getCrop(@PathVariable Long id){
        CropDTO cropDTO = cropService.getCropById(id);
        return new ResponseEntity<>(cropDTO, HttpStatus.OK);
    }
}