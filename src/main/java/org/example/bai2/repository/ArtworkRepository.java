package org.example.bai2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.bai2.entity.Artwork;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
}