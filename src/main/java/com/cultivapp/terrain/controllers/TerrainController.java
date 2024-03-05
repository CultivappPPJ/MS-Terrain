package com.cultivapp.terrain.controllers;

import com.cultivapp.terrain.service.TerrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/terrain")
@CrossOrigin("http://localhost:5173/")
public class TerrainController {
    private final TerrainService terrainService;

    @GetMapping("/crud/create")
    public String helloWorld(){
        return "Hello World!";
    }
    @GetMapping("/crud/delete")
    public String delete(){
        return "Delete";
    }

    @GetMapping("/hello2")
    public String helloWorldDos(){
        return "Hello World Dos!";
    }
}
