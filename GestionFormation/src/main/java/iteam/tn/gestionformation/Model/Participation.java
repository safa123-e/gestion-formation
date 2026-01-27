package iteam.tn.gestionformation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private SessionFormation session;

    @Column(name = "utilisateur_id", nullable = false)
    private Long utilisateurId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    private Boolean statut;

    @Column(name = "date_demande")
    private LocalDateTime dateDemande;

    @Column(name = "date_decision")
    private LocalDateTime dateDecision;

    @Column(name = "decide_par_utilisateur_id")
    private Long decideParUtilisateurId;

    @Column(name = "commentaire_decision")
    private String commentaireDecision;
}
