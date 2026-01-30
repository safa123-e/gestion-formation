package ServiceTest;


import iteam.tn.gestionformation.Dto.FormationCreateDto;
import iteam.tn.gestionformation.Repository.FormationRepository;
import iteam.tn.gestionformation.Repository.TypeFormationRepository;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.TypeFormation;
import iteam.tn.gestionformation.service.FormationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitaires - Formation Service")
class FormationServiceTest {

    @Mock
    private FormationRepository formationRepository;

    @Mock
    private TypeFormationRepository typeFormationRepository;

    @InjectMocks
    private FormationService formationService;

    @Test
    @DisplayName("✅ Succès : Créer une formation")
    void creerFormation_Success() {
        // Given
        FormationCreateDto dto = new FormationCreateDto();
        dto.setTitre("Java Test");
        dto.setTypeFormationId(1L);
        dto.setStatut(true);

        TypeFormation type = new TypeFormation();
        type.setId(1L);

        when(typeFormationRepository.findById(1L)).thenReturn(Optional.of(type));
        when(formationRepository.save(any(Formation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Formation result = formationService.creerFormation(dto, 123);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Java Test");
        assertThat(result.getCreeParAdminId()).isEqualTo(123);
        verify(formationRepository, times(1)).save(any(Formation.class));
    }

    @Test
    @DisplayName("❌ Erreur : Type formation introuvable")
    void creerFormation_TypeNotFound() {
        // Given
        FormationCreateDto dto = new FormationCreateDto();
        dto.setTypeFormationId(99L);

        when(typeFormationRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then (Couverture du bloc catch RuntimeException)
        assertThatThrownBy(() -> formationService.creerFormation(dto, 123))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Type de formation introuvable");
    }

    @Test
    @DisplayName("✅ Succès : Modifier une formation")
    void modifierFormation_Success() {
        // Given
        Long id = 1L;
        Formation existing = new Formation();
        existing.setId(id);
        existing.setTitre("Ancien Titre");

        Formation updatedData = new Formation();
        updatedData.setTitre("Nouveau Titre");

        when(formationRepository.findById(id)).thenReturn(Optional.of(existing));
        when(formationRepository.save(any(Formation.class))).thenReturn(existing);

        // When
        Formation result = formationService.modifierFormation(id, updatedData);

        // Then
        assertThat(result.getTitre()).isEqualTo("Nouveau Titre");
    }

    @Test
    @DisplayName("✅ Succès : Consulter toutes les formations")
    void consulterFormations_Success() {
        // Given
        when(formationRepository.findAll()).thenReturn(Arrays.asList(new Formation(), new Formation()));

        // When
        List<Formation> list = formationService.consulterFormations();

        // Then
        assertThat(list).hasSize(2);
    }

    @Test
    @DisplayName("✅ Succès : Supprimer une formation")
    void supprimerFormation_Success() {
        // When
        formationService.supprimerFormation(1L);

        // Then
        verify(formationRepository, times(1)).deleteById(1L);
    }
}
