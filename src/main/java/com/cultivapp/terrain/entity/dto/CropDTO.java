package com.cultivapp.terrain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {
    private Long id;
    private SeedTypeDTO seedType;
    private String area;
    private String photo;
    private LocalDate harvestDate;
    private boolean forSale;
}