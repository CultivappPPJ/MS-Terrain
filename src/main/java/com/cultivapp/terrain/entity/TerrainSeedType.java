package com.cultivapp.terrain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrainSeedType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "terrain_id")
    private Terrain terrain;

    @ManyToOne
    @JoinColumn(name = "seed_type_id")
    private SeedType seedType;
}
