package ServiceTest;


import iteam.tn.gestionformation.Repository.TypeFormationRepository;
import iteam.tn.gestionformation.model.TypeFormation;
import iteam.tn.gestionformation.service.TypeFormationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitaires - Type Formation Service")
class TypeFormationServiceTest {

    @Mock
    private TypeFormationRepository repository;

    @InjectMocks
    private TypeFormationService service;

    @Test
    @DisplayName("✅ Succès : Créer un nouveau type")
    void creerType_Success() {
        // Given
        String nom = "Technique";
        String desc = "Formations IT";

        when(repository.existsByNom(nom)).thenReturn(false);
        when(repository.save(any(TypeFormation.class))).thenAnswer(i -> i.getArgument(0));

        // When
        TypeFormation result = service.creerType(nom, desc);

        // Then
        assertThat(result.getNom()).isEqualTo(nom);
        assertThat(result.getDescription()).isEqualTo(desc);
        verify(repository, times(1)).save(any(TypeFormation.class));
    }

    @Test
    @DisplayName("❌ Erreur : Type déjà existant")
    void creerType_AlreadyExists() {
        // Given
        String nom = "Soft Skills";
        when(repository.existsByNom(nom)).thenReturn(true);

        // When & Then
        // Cette branche est CRUCIALE pour JaCoCo (Couverture du IF)
        assertThatThrownBy(() -> service.creerType(nom, "description"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Type déjà existant");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("✅ Succès : Lister les types")
    void listerTypes_Success() {
        // Given
        when(repository.findAll()).thenReturn(List.of(new TypeFormation(), new TypeFormation()));

        // When
        List<TypeFormation> list = service.listerTypes();

        // Then
        assertThat(list).hasSize(2);
        verify(repository).findAll();
    }
}