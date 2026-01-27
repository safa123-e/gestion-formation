package iteam.tn.gestionformation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "session_formation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @Column(name = "date_debut")
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    private String lieu;
    private String formateur;
    private Integer capacite;

    // ✅ NOUVELLE RELATION
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AffectationFormationService> affectations = new ArrayList<>();
}
