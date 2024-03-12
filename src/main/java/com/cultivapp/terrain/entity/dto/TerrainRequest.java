package com.cultivapp.terrain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TerrainRequest {
    private Long id;
    private String area;
    private String soilType;
    private List<Long> seedTypeIds;
    private String photo;
    private String email;
    private Long remainingDays;
    private boolean forSale;
    private String fullName;
}
