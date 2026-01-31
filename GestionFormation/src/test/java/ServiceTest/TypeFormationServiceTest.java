package ServiceTest;


import iteam.tn.gestionformation.Repository.TypeFormationRepository;
import iteam.tn.gestionformation.model.TypeFormation;
import iteam.tn.gestionformation.service.TypeFormationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitaires - Type Formation Service")
class TypeFormationServiceTest {

    @Mock
    private TypeFormationRepository repository;

    @InjectMocks
    private TypeFormationService typeFormationService;

    private TypeFormation typeFormation;

    @BeforeEach
    void setUp() {
        typeFormation = TypeFormation.builder()
                .id(1L)
                .nom("Soft Skills")
                .description("Communication et Leadership")
                .build();
    }

    @Test
    void creerType_Success() {
        // GIVEN
        String nom = "Soft Skills";
        String desc = "Communication et Leadership";

        when(repository.existsByNom(nom)).thenReturn(false);
        when(repository.save(any(TypeFormation.class))).thenReturn(typeFormation);

        // WHEN
        TypeFormation result = typeFormationService.creerType(nom, desc);

        // THEN
        assertNotNull(result);
        assertEquals(nom, result.getNom());
        verify(repository, times(1)).save(any(TypeFormation.class));
    }

    @Test
    void creerType_AlreadyExists_ShouldThrowException() {
        // GIVEN
        String nom = "Soft Skills";
        when(repository.existsByNom(nom)).thenReturn(true);

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            typeFormationService.creerType(nom, "description");
        });

        assertEquals("Type déjà existant", exception.getMessage());
        // On vérifie que save n'est jamais appelé si le nom existe déjà
        verify(repository, never()).save(any(TypeFormation.class));
    }

    @Test
    void listerTypes_ShouldReturnList() {
        // GIVEN
        List<TypeFormation> mockList = Arrays.asList(
                typeFormation,
                TypeFormation.builder().nom("Technical").build()
        );
        when(repository.findAll()).thenReturn(mockList);

        // WHEN
        List<TypeFormation> result = typeFormationService.listerTypes();

        // THEN
        assertEquals(2, result.size());
        assertEquals("Soft Skills", result.get(0).getNom());
        verify(repository, times(1)).findAll();
    }

}