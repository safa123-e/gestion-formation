package servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.demo.model.PrivilegeGa;
import com.demo.model.ProfilGa;
import com.demo.model.ProfilPrivilegeGa;
import com.demo.repository.ProfilPrivilegeGaRepository;
import com.demo.services.PrivilegeGaService;
import com.demo.tools.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrivilegeGaServiceTest {

    @Mock
    private ProfilPrivilegeGaRepository profilPrivilegeGaRepository;

    @InjectMocks
    private PrivilegeGaService privilegeGaService;

    private ProfilGa profilGa;
    private PrivilegeGa privilege1;
    private PrivilegeGa privilege2;
    private ProfilPrivilegeGa profilPrivilege1;
    private ProfilPrivilegeGa profilPrivilege2;

    @BeforeEach
    void setUp() {
        profilGa = new ProfilGa();
        profilGa.setIdProfilGa("ADMIN");

        privilege1 = new PrivilegeGa();
        privilege1.setIdPrivilegeGa("ADMIN");
        privilege1.setLibPrivilegeArGa("LECTURE");

        privilege2 = new PrivilegeGa();
        privilege2.setIdPrivilegeGa("ADMIN1");
        privilege2.setLibPrivilegeArGa("ECRITURE");

        // Objets de liaison
        profilPrivilege1 = new ProfilPrivilegeGa();
        profilPrivilege1.setPrivilegeGa(privilege1);
        profilPrivilege1.setEtatPrivProfilGa(1);

        profilPrivilege2 = new ProfilPrivilegeGa();
        profilPrivilege2.setPrivilegeGa(privilege2);
        profilPrivilege2.setEtatPrivProfilGa(1);
    }

    @Test
    void findByProfilGa_Success() {
        // GIVEN
        List<ProfilPrivilegeGa> mockResults = Arrays.asList(profilPrivilege1, profilPrivilege2);
        when(profilPrivilegeGaRepository.findByProfilGaAndEtatPrivProfilGa(profilGa, 1))
                .thenReturn(mockResults);

        // WHEN
        List<PrivilegeGa> result = privilegeGaService.findByProfilGa(profilGa);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("LECTURE", result.get(0).getLibPrivilegeArGa());
        assertEquals("ECRITURE", result.get(1).getLibPrivilegeArGa());
        verify(profilPrivilegeGaRepository, times(1))
                .findByProfilGaAndEtatPrivProfilGa(profilGa, 1);
    }

    @Test
    void findByProfilGa_EmptyList_ShouldReturnEmptyList() {
        // GIVEN
        when(profilPrivilegeGaRepository.findByProfilGaAndEtatPrivProfilGa(any(), anyInt()))
                .thenReturn(Arrays.asList());

        // WHEN
        List<PrivilegeGa> result = privilegeGaService.findByProfilGa(profilGa);

        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    void findByProfilGa_Exception_ShouldThrowGlobalException() {
        // GIVEN
        when(profilPrivilegeGaRepository.findByProfilGaAndEtatPrivProfilGa(any(), anyInt()))
                .thenThrow(new RuntimeException("Erreur SQL"));

        // WHEN & THEN
        assertThrows(GlobalExceptionHandler.class, () -> {
            privilegeGaService.findByProfilGa(profilGa);
        });
    }
}