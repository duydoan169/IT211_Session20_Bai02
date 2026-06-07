package org.example.bai2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.bai2.dto.ArtworkDTO;
import org.example.bai2.service.ArtworkService;

import java.util.List;

@RestController
@RequestMapping("/api/gallery/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @GetMapping
    public List<ArtworkDTO> getAllArtworks() {

        return artworkService.getAllArtworks();
    }
}