package iteam.tn.gestionformation.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "formation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    private Boolean statut;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "type_formation_id", nullable = false)
    private TypeFormation typeFormation;

    @Column(name = "cree_par_admin_id")
    private Integer creeParAdminId;
}

