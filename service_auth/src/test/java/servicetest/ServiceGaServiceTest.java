package servicetest;

import com.demo.model.ServiceGa;
import com.demo.repository.ServiceGaRepository;
import com.demo.services.ServiceGaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

public class ServiceGaServiceTest {
    @Mock
    private ServiceGaRepository serviceGaRepository;

    @InjectMocks
    private ServiceGaService serviceGaService;

    @Test
    void findServiceGaById_shouldReturnServiceGa_whenExists() {
        // Arrange
        Integer id = 1;
        ServiceGa serviceGa = new ServiceGa();
        serviceGa.setIdServiceGt(id);
        when(serviceGaRepository.findById(id)).thenReturn(Optional.of(serviceGa));
        // Act
        ServiceGa result = serviceGaService.findServiceGaById(id);
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdServiceGt()).isEqualTo(id) ;
        verify(serviceGaRepository, times(1)).findById(id);
    }
    @Test
    void findServiceGaById_shouldThrowException_whenNotFound() {
        // Arrange
        Integer id = 99;
        when(serviceGaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceGaService.findServiceGaById(id);
        });

        assertEquals(
                "ServiceGa introuvable avec cet identifiant: " + id,
                exception.getMessage()
        );

        verify(serviceGaRepository, times(1)).findById(id);
    }

}
