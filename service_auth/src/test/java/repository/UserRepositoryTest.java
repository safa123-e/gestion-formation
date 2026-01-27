package repository;

import com.demo.FormationServiceApplication;
import com.demo.model.AgentGa;
import com.demo.model.AgentProfilAppGa;
import com.demo.model.EtatAgentGa;
import com.demo.repository.AgentGaRepository;
import com.demo.repository.AgentPrivGaRepository;
import com.demo.repository.AgentProfilAppGaRepository;
import com.demo.repository.EtatAgentGaRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = FormationServiceApplication.class)
@EnableJpaRepositories(basePackages = "com.demo.repository")
@EntityScan(basePackages = "com.demo.model")
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private  AgentGaRepository userRepository ;
    @Autowired
    private AgentProfilAppGaRepository agentProfilAppGa ;
    @Autowired
    private AgentPrivGaRepository agentPrivGaRepository ;
    @Autowired
    private EtatAgentGaRepo etatAgentGaRepo ;

    @BeforeEach
    void setUp() {
        // ⚠️ si tu as des FK vers agent_ga, deleteAll peut échouer
        agentProfilAppGa.deleteAll();
        agentPrivGaRepository.deleteAll();
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
        etatAgentGaRepo.save(etatActif());
        AgentGa saved = userRepository.save(user);

        Optional<AgentGa> found = userRepository.findById(saved.getIdAgentGa());

        assertThat(found).isPresent();
        assertThat(found.get().getNomPrenomAgentGa()).isEqualTo("Ahmed");
    }

    @Test
    @DisplayName("Doit retourner tous les utilisateurs")
    void findAll() {
        etatAgentGaRepo.save(etatActif());

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
        etatAgentGaRepo.save(etatActif());

        AgentGa user = new AgentGa();
        user.setNomPrenomAgentGa("Ahmed");
        user.setEmailAgentGa("ahmed@email.com");
        user.setIsIsieGa(true);
        user.setEtatAgentGa(etatActif());
        user.setPwdAgentGa("0000");
        user.setCinAgentGa("11111111");

        AgentGa saved = userRepository.save(user);

        userRepository.deleteById(saved.getIdAgentGa());

        assertThat(userRepository.existsById(saved.getIdAgentGa())).isFalse();
    }

    @Test
    @DisplayName("Doit mettre à jour un utilisateur")
    void updateUser() {
        etatAgentGaRepo.save(etatActif());

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

        Optional<AgentGa> updated = userRepository.findById(saved.getIdAgentGa());

        assertThat(updated).isPresent();
        assertThat(updated.get().getNomPrenomAgentGa()).isEqualTo("Ahmed Updated");
    }
}
