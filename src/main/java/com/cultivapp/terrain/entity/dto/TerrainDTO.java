package com.cultivapp.terrain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrainDTO {
    private Long id;
    private String name;
    private String area;
    private String soilType;
    private String photo;
    private String email;
    private LocalDate remainingDays;
    private boolean forSale;
    private String fullName;
    private List<SeedTypeDTO> seedTypes;
    private String location;
}