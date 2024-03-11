package com.cultivapp.terrain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seed_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeedType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "seedTypes")
    private List<Terrain> terrains = new ArrayList<>();
}
