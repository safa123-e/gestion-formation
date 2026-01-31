package ControllerTest;

import iteam.tn.gestionformation.Dto.FormationSearchDto;
import iteam.tn.gestionformation.Repository.*;
import iteam.tn.gestionformation.Specification.FormationSpecification;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.TypeFormation;

import iteam.tn.gestionformation.service.SessionFormationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = iteam.tn.gestionformation.GestionFormationApplication.class)

public class FormationSpecificationTest {

    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private SessionFormationRepository SessionFormationRepository;
    @Autowired
    private AffectationFormationServiceRepository afsessionControllerTest;
    @Autowired
    private ParticipationRepository ParticipationRepository;
    @Autowired
    private FeedbackRepository fedback;
    @Autowired
    private TypeFormationRepository typeFormationRepository;

    private TypeFormation typeAngular;
    private TypeFormation typeSpring;

    @BeforeEach
    void setUp() {
        fedback.deleteAll();
        ParticipationRepository.deleteAll();
        afsessionControllerTest.deleteAll();
        SessionFormationRepository.deleteAll();
        formationRepository.deleteAll();

        // Créer les types
        typeAngular = TypeFormation.builder().nom("Angular").description("Front-end").build();
        typeSpring = TypeFormation.builder().nom("Spring").description("Back-end").build();
        typeFormationRepository.saveAll(List.of(typeAngular, typeSpring));

        // Créer les formations
        Formation f1 = Formation.builder()
                .titre("Formation Angular Avancée")
                .statut(true)
                .typeFormation(typeAngular)
                .dateCreation(LocalDateTime.now().minusDays(10))
                .build();

        Formation f2 = Formation.builder()
                .titre("Formation Spring Boot")
                .statut(false)
                .typeFormation(typeSpring)
                .dateCreation(LocalDateTime.now().minusDays(5))
                .build();

        Formation f3 = Formation.builder()
                .titre("Introduction Angular")
                .statut(true)
                .typeFormation(typeAngular)
                .dateCreation(LocalDateTime.now().minusDays(2))
                .build();

        formationRepository.saveAll(List.of(f1, f2, f3));
    }

    @Test
    void testFilterByTitre() {
        FormationSearchDto dto = new FormationSearchDto();
        dto.setTitre("angular");

        Specification<Formation> spec = FormationSpecification.withCriteria(dto);
        List<Formation> result = formationRepository.findAll(spec);

        assertThat(result).hasSize(2)
                .extracting("titre")
                .allMatch(t -> t.toString().contains("Angular"));
    }

    @Test
    void testFilterByStatut() {
        FormationSearchDto dto = new FormationSearchDto();
        dto.setStatut(true);

        List<Formation> result = formationRepository.findAll(FormationSpecification.withCriteria(dto));

        assertThat(result).hasSize(2)
                .allMatch(Formation::getStatut);
    }

    @Test
    void testFilterByTypeFormation() {
        FormationSearchDto dto = new FormationSearchDto();
        dto.setTypeFormationId(typeSpring.getId());

        List<Formation> result = formationRepository.findAll(FormationSpecification.withCriteria(dto));

        assertThat(result).hasSize(1)
                .allMatch(f -> f.getTypeFormation().getId().equals(typeSpring.getId()));
    }

    @Test
    void testFilterByDateRange() {
        FormationSearchDto dto = new FormationSearchDto();
        dto.setDateDebut(LocalDateTime.now().minusDays(6));
        dto.setDateFin(LocalDateTime.now());

        List<Formation> result = formationRepository.findAll(FormationSpecification.withCriteria(dto));

        assertThat(result).hasSize(2)
                .allMatch(f -> !f.getDateCreation().isBefore(dto.getDateDebut()) &&
                        !f.getDateCreation().isAfter(dto.getDateFin()));
    }

    @Test
    void testFilterCombined() {
        FormationSearchDto dto = new FormationSearchDto();
        dto.setTitre("angular");
        dto.setStatut(true);

        List<Formation> result = formationRepository.findAll(FormationSpecification.withCriteria(dto));

        assertThat(result).hasSize(2)
                .allMatch(f -> f.getTitre().toLowerCase().contains("angular") && f.getStatut());
    }
}
