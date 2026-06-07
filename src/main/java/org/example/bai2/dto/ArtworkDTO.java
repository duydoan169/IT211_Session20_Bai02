package org.example.bai2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtworkDTO {

    private Long id;

    private String title;

    private String description;

    private boolean published;

    private String ownerName;
}