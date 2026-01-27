package iteam.tn.gestionformation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "affectation_formation_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AffectationFormationService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private SessionFormation session;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "date_affectation")
    private LocalDateTime dateAffectation;
}
