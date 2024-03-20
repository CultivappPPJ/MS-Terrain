package com.cultivapp.terrain.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @OneToMany(mappedBy = "seedType")
    private Set<Crop> crops = new HashSet<>();
}