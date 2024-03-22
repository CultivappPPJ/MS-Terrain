    package com.cultivapp.terrain.entity;

    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDate;

    @Entity
    @Table(name = "crop")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class Crop {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "seed_type_id", referencedColumnName = "id")
        private SeedType seedType;
        private String area;
        private String photo;
        private LocalDate harvestDate;
        private boolean forSale;
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "terrain_id", nullable = false)
        private Terrain terrain;
        private boolean enabled;
    }
