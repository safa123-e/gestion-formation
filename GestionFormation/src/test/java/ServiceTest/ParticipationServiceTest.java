package ServiceTest;


import iteam.tn.gestionformation.Dto.FormationUserDto;
import iteam.tn.gestionformation.Repository.ParticipationRepository;
import iteam.tn.gestionformation.Repository.SessionFormationRepository;
import iteam.tn.gestionformation.model.Formation;
import iteam.tn.gestionformation.model.Participation;
import iteam.tn.gestionformation.model.SessionFormation;
import iteam.tn.gestionformation.service.ParticipationService;
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
@DisplayName("Tests Unitaires - Participation Service")
class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private SessionFormationRepository sessionFormationRepository;

    @InjectMocks
    private ParticipationService participationService;

    @Test
    @DisplayName("✅ Succès : Créer des participations (avec gestion doublons)")
    void creerParticipations_Success() {
        // Given
        Long sessionId = 1L;
        SessionFormation session = new SessionFormation();
        session.setId(sessionId);

        // On teste 2 utilisateurs : un nouveau et un qui est déjà inscrit (doublon)
        List<Integer> userIds = Arrays.asList(101, 102);

        when(sessionFormationRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Simulation : 101 est déjà inscrit (doublon), 102 est nouveau
        when(participationRepository.existsBySessionAndUtilisateurId(session, 101)).thenReturn(true);
        when(participationRepository.existsBySessionAndUtilisateurId(session, 102)).thenReturn(false);

        when(participationRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        // When
        List<Participation> result = participationService.creerParticipations(sessionId, userIds, 5, 999);

        // Then
        assertThat(result).hasSize(1); // Seul l'utilisateur 102 doit être ajouté
        assertThat(result.get(0).getUtilisateurId()).isEqualTo(102);
        verify(participationRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("❌ Erreur : Session introuvable")
    void creerParticipations_SessionNotFound() {
        when(sessionFormationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> participationService.creerParticipations(1L, Arrays.asList(1), 1, 1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Session introuvable");
    }

    @Test
    @DisplayName("✅ Succès : Mapper les participations vers DTO")
    void formationsParUtilisateur_Success() {
        // Given
        Integer userId = 101;

        Formation f = new Formation();
        f.setId(50L); f.setTitre("Java Test");

        SessionFormation s = new SessionFormation();
        s.setId(1L); s.setFormation(f);

        Participation p = new Participation();
        p.setSession(s); p.setStatut(true);

        when(participationRepository.findByUtilisateurId(userId)).thenReturn(List.of(p));

        // When
        List<FormationUserDto> result = participationService.formationsParUtilisateur(userId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitreFormation()).isEqualTo("Java Test");
    }
}