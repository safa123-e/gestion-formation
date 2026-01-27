package iteam.tn.gestionformation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_formation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;
}

