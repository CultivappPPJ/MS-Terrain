package com.cultivapp.terrain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CropRequest {
    private Long seedTypeId;
    private String area;
    private String photo;
    private LocalDate harvestDate;
    private boolean forSale;
    private Long terrainId;
}