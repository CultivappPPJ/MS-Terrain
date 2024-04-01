package com.cultivapp.terrain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerrainRequest {
    private String name;
    private String area;
    private String soilType;
    private String photo;
    private String location;
    private String email;
    private String fullName;
}
