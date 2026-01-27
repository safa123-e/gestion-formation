package repository;

import com.demo.FormationServiceApplication;
import com.demo.model.AgentGa;
import com.demo.model.EtatAgentGa;
import com.demo.repository.AgentGaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@SpringBootTest(classes = FormationServiceApplication.class)

@DisplayName("Tests du repository User (AgentGaRepository)")
class UserRepositoryTest {

    @Autowired
    private AgentGaRepository userRepository;

    @BeforeEach
    void setUp() {
        // ⚠️ si tu as des FK vers agent_ga, deleteAll peut échouer
        // Dans ce cas, supprime d'abord les tables enfants via leurs repositories.
        userRepository.deleteAll();
    }

    private EtatAgentGa etatActif() {
        EtatAgentGa etat = new EtatAgentGa();
        etat.setIdEtatAgentGa(1);
        etat.setLibEtatAgentGa("active");
        return etat;
    }

    @Test
    @DisplayName("Doit sauvegarder et retrouver un utilisateur")
    void saveAndFind() {
        AgentGa user = new AgentGa();
        user.setNomPrenomAgentGa("Ahmed");
        user.setEmailAgentGa("ahmed@email.com"); // adapte si ton champ s'appelle autrement
        user.setIsIsieGa(true);              // adapte si besoin
        user.setEtatAgentGa(etatActif());        // ⚠️ seulement si relation OK
        user.setPwdAgentGa("0000");
        user.setCinAgentGa("11111111");

        AgentGa saved = userRepository.save(user);

        Optional<AgentGa> found = userRepository.findById(saved.getIdUserAgentGa());

        assertThat(found).isPresent();
        assertThat(found.get().getNomPrenomAgentGa()).isEqualTo("Ahmed");
    }

    @Test
    @DisplayName("Doit retourner tous les utilisateurs")
    void findAll() {
        AgentGa u1 = new AgentGa();
        u1.setNomPrenomAgentGa("Ahmed");
        u1.setEmailAgentGa("ahmed@email.com");
        u1.setIsIsieGa(true);
        u1.setEtatAgentGa(etatActif());
        u1.setPwdAgentGa("0000");
        u1.setCinAgentGa("11111111");

        AgentGa u2 = new AgentGa();
        u2.setNomPrenomAgentGa("Fatma");
        u2.setEmailAgentGa("fatma@email.com");
        u2.setIsIsieGa(true);
        u2.setEtatAgentGa(etatActif());
        u2.setPwdAgentGa("0000");
        u2.setCinAgentGa("22222222");

        userRepository.saveAll(List.of(u1, u2));

        List<AgentGa> users = userRepository.findAll();

        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Doit supprimer un utilisateur")
    void deleteUser() {
        AgentGa user = new AgentGa();
        user.setNomPrenomAgentGa("Ahmed");
        user.setEmailAgentGa("ahmed@email.com");
        user.setIsIsieGa(true);
        user.setEtatAgentGa(etatActif());
        user.setPwdAgentGa("0000");
        user.setCinAgentGa("11111111");

        AgentGa saved = userRepository.save(user);

        userRepository.deleteById(saved.getIdUserAgentGa());

        assertThat(userRepository.existsById(saved.getIdUserAgentGa())).isFalse();
    }

    @Test
    @DisplayName("Doit mettre à jour un utilisateur")
    void updateUser() {
        AgentGa user = new AgentGa();
        user.setNomPrenomAgentGa("Ahmed");
        user.setEmailAgentGa("ahmed@email.com");
        user.setIsIsieGa(true);
        user.setEtatAgentGa(etatActif());
        user.setPwdAgentGa("0000");
        user.setCinAgentGa("11111111");

        AgentGa saved = userRepository.save(user);

        saved.setNomPrenomAgentGa("Ahmed Updated");
        userRepository.save(saved);

        Optional<AgentGa> updated = userRepository.findById(saved.getIdUserAgentGa());

        assertThat(updated).isPresent();
        assertThat(updated.get().getNomPrenomAgentGa()).isEqualTo("Ahmed Updated");
    }
}
