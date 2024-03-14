package com.cultivapp.terrain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    @ManyToMany(mappedBy = "seedTypes", fetch = FetchType.EAGER)
    private Set<Terrain> terrains = new HashSet<>();
}