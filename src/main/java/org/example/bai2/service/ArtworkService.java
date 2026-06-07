package org.example.bai2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.bai2.dto.ArtworkDTO;
import org.example.bai2.entity.Artwork;
import org.example.bai2.repository.ArtworkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    public List<ArtworkDTO> getAllArtworks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        List<Artwork> artworks = artworkRepository.findAll();
        if (roles.contains("ROLE_ADMIN")) {
            return artworks.stream().map(this::toDTO).toList();
        }
        return artworks.stream().filter(artwork -> artwork.isPublished() || artwork.getOwner().getUsername().equals(username)).map(this::toDTO).toList();
    }

    private ArtworkDTO toDTO(Artwork artwork) {

        return ArtworkDTO.builder().id(artwork.getId()).title(artwork.getTitle()).description(artwork.getDescription()).published(artwork.isPublished()).ownerName(artwork.getOwner().getUsername()).build();
    }
}