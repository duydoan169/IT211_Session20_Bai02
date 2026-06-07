package org.example.bai2.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "artworks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "is_published")
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Account owner;
}