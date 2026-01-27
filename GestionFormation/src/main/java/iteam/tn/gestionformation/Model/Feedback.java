package iteam.tn.gestionformation.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participation_id", nullable = false, unique = true)
    private Participation participation;

    private Integer note;

    private String commentaire;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
}
