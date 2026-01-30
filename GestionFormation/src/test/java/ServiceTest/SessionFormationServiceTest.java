package ServiceTest;


import iteam.tn.gestionformation.Dto.SessionCreateDto;
import iteam.tn.gestionformation.Dto.ServiceCapacityDto;
import iteam.tn.gestionformation.Repository.*;
import iteam.tn.gestionformation.comp.AuthClient;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.service.SessionFormationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import iteam.tn.gestionformation.model.SessionFormation ;
import java.time.LocalDateTime;
import java.util.*;
import iteam.tn.gestionformation.model.AffectationFormationService ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import  iteam.tn.gestionformation.model.Participation ;
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitaires - Session Formation Service")
class SessionFormationServiceTest {

    @Mock private SessionFormationRepository sessionRepository;
    @Mock private FormationRepository formationRepository;
    @Mock private AffectationFormationServiceRepository affectationRepo;
    @Mock private ParticipationRepository participationRepo;
    @Mock private AuthClient authClient;

    @InjectMocks
    private SessionFormationService sessionService;

    @Test
    @DisplayName("✅ Succès : Créer une session avec affectations")
    void creerSession_Success() {
        // Given
        ServiceCapacityDto serviceDto = new ServiceCapacityDto();
        serviceDto.setServiceId(1L);
        serviceDto.setCapacite(10);

        SessionCreateDto dto = new SessionCreateDto();
        dto.setFormationId(1L);
        dto.setServices(List.of(serviceDto));

        iteam.tn.gestionformation.model.Formation formation = new Formation();
        formation.setId(1L);

        when(formationRepository.findById(1L)).thenReturn(Optional.of(formation));
        when(sessionRepository.save(any(iteam.tn.gestionformation.model.SessionFormation.class))).thenAnswer(i -> i.getArgument(0));

        // When
        SessionFormation result = sessionService.creerSession(dto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAffectations()).hasSize(1);
        verify(sessionRepository).save(any(SessionFormation.class));
    }

    @Test
    @DisplayName("✅ Notifications : Cas Responsable (Filtre sans participants)")
    void getNotifications_Responsable() {
        // Given
        String token = "Bearer test-token";
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("idService", 1);
        userInfo.put("isResponsable", true);

        SessionFormation session1 = SessionFormation.builder().id(10L).build();
        AffectationFormationService aff = AffectationFormationService.builder().session(session1).build();

        when(authClient.getUserIdConnecte(token)).thenReturn(userInfo);
        when(affectationRepo.findByServiceId(1)).thenReturn(List.of(aff));
        // On simule 0 participant pour que le filtre laisse passer la session
        when(participationRepo.countBySessionId(10L)).thenReturn(0);

        // When
        List<SessionFormation> result = sessionService.getNotifications(token);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("✅ Notifications : Cas Agent (Participations statut null)")
    void getNotifications_Agent() {
        // Given
        String token = "Bearer test-token";
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("idAgent", 100);
        userInfo.put("isResponsable", false);

        SessionFormation session2 = SessionFormation.builder().id(20L).build();
        Participation part = new Participation();
        part.setSession(session2);

        when(authClient.getUserIdConnecte(token)).thenReturn(userInfo);
        when(participationRepo.findByUtilisateurIdAndStatutIsNull(100)).thenReturn(List.of(part));

        // When
        List<SessionFormation> result = sessionService.getNotifications(token);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(20L);
    }

    @Test
    @DisplayName("❌ Erreur : Formation introuvable lors de la création")
    void creerSession_FormationNotFound() {
        SessionCreateDto dto = new SessionCreateDto();
        dto.setFormationId(99L);

        when(formationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sessionService.creerSession(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Formation introuvable");
    }
}