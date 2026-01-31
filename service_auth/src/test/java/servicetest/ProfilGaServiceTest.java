package servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.demo.model.ProfilGa;
import com.demo.repository.ProfilGaRepository;
import com.demo.services.ProfilGaService;
import com.demo.tools.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProfilGaServiceTest {

    @Mock
    private ProfilGaRepository profilGaRepository;

    @InjectMocks
    private ProfilGaService profilGaService;

    private ProfilGa profil;
    private final String ID_PROFIL = "ADMIN_GA";

    @BeforeEach
    void setUp() {
        profil = new ProfilGa();
        profil.setIdProfilGa(ID_PROFIL);
        profil.setLibProfilGaAr("Administrateur Gestion");
    }

    @Test
    void findProfilById_Success() {
        // GIVEN
        when(profilGaRepository.findById(ID_PROFIL)).thenReturn(Optional.of(profil));

        // WHEN
        ProfilGa result = profilGaService.findProfilById(ID_PROFIL);

        // THEN
        assertNotNull(result);
        assertEquals(ID_PROFIL, result.getIdProfilGa());
        verify(profilGaRepository, times(1)).findById(ID_PROFIL);
    }

    @Test
    void findProfilById_NotFound_ShouldThrowGlobalException() {
        // GIVEN
        when(profilGaRepository.findById("INCONNU")).thenReturn(Optional.empty());

        // WHEN & THEN
        // Votre code attrape l'exception et la transforme en GlobalExceptionHandler
        GlobalExceptionHandler exception = assertThrows(GlobalExceptionHandler.class, () -> {
            profilGaService.findProfilById("INCONNU");
        });

        assertTrue(exception.getMessage().contains("Impossible de récupérer le profil"));
    }

    @Test
    void findProfilById_RepositoryError_ShouldThrowGlobalException() {
        // GIVEN : On simule une erreur base de données (ex: connexion perdue)
        when(profilGaRepository.findById(anyString())).thenThrow(new RuntimeException("DB Error"));

        // WHEN & THEN
        assertThrows(GlobalExceptionHandler.class, () -> {
            profilGaService.findProfilById(ID_PROFIL);
        });
    }
}