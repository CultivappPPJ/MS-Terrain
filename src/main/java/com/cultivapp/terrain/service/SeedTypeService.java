package com.cultivapp.terrain.service;

import com.cultivapp.terrain.entity.SeedType;
import com.cultivapp.terrain.repository.SeedTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedTypeService {

    private final SeedTypeRepository seedTypeRepository;

    public List<SeedType> getAllSeedTypes() {
        return seedTypeRepository.findAll();
    }
}