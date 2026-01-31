package ServiceTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.Repository.FormationRepository;
import iteam.tn.gestionformation.Repository.TypeFormationRepository;
import iteam.tn.gestionformation.service.FormationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import iteam.tn.gestionformation.model.TypeFormation ;
import iteam.tn.gestionformation.model.Formation ;
@ExtendWith(MockitoExtension.class)
class FormationServiceTest {

    @Mock
    private FormationRepository formationRepository;

    @Mock
    private TypeFormationRepository typeFormationRepository;

    @InjectMocks
    private FormationService formationService;

    private FormationCreateDto createDto;
    private  TypeFormation typeFormation;
    private Formation formation;

    @BeforeEach
    void setUp() {
        // Initialisation du DTO
        createDto = new FormationCreateDto();
        createDto.setTitre("Java Spring Boot");
        createDto.setDescription("Cours complet");
        createDto.setStatut(true);
        createDto.setTypeFormationId(10L);

        // Initialisation de l'entité TypeFormation
        typeFormation = new TypeFormation();
        typeFormation.setId(10L);
        typeFormation.setNom("Informatique");

        // Initialisation de l'entité Formation (résultat attendu)
        formation = Formation.builder()
                .id(1L)
                .titre(createDto.getTitre())
                .typeFormation(typeFormation)
                .creeParAdminId(123)
                .build();
    }

    @Test
    void creerFormation_Success() {
        // GIVEN
        when(typeFormationRepository.findById(10L)).thenReturn(Optional.of(typeFormation));
        when(formationRepository.save(any(Formation.class))).thenReturn(formation);

        // WHEN
        Formation result = formationService.creerFormation(createDto, 123);

        // THEN
        assertNotNull(result);
        assertEquals("Java Spring Boot", result.getTitre());
        assertEquals(123, result.getCreeParAdminId());
        verify(formationRepository, times(1)).save(any(Formation.class));
    }

    @Test
    void creerFormation_TypeNotFound_ShouldThrowException() {
        // GIVEN
        when(typeFormationRepository.findById(10L)).thenReturn(Optional.empty());

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            formationService.creerFormation(createDto, 123);
        });

        assertTrue(exception.getMessage().contains("Type de formation introuvable"));
        verify(formationRepository, never()).save(any(Formation.class));
    }

    @Test
    void getById_Success() {
        // GIVEN
        when(formationRepository.findById(1L)).thenReturn(Optional.of(formation));

        // WHEN
        Formation result = formationService.getById(1L);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void update_Success() {
        // GIVEN
        when(formationRepository.findById(1L)).thenReturn(Optional.of(formation));
        when(typeFormationRepository.findById(10L)).thenReturn(Optional.of(typeFormation));
        when(formationRepository.save(any(Formation.class))).thenReturn(formation);

        // WHEN
        Formation updated = formationService.update(1L, createDto);

        // THEN
        assertNotNull(updated);
        verify(formationRepository).save(any(Formation.class));
    }

    @Test
    void supprimerFormation_ShouldInvokeDelete() {
        // WHEN
        formationService.supprimerFormation(1L);

        // THEN
        verify(formationRepository, times(1)).deleteById(1L);
    }
}