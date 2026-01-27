/*package bdd;


import com.demo.model.AgentGa;
import com.demo.model.EtatAgentGa;
import com.demo.repository.AgentGaRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.*;
import io.cucumber.datatable.DataTable;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

public class UserStepDefinitions {

    @Autowired
    private AgentGaRepository userRepository;
    private EtatAgentGa etat ;

    private List<AgentGa> userList;

    @Before
    public void setup() {
        userRepository.deleteAll();
    }


    @Etantdonné("une base de données vide")
    public void une_base_de_donnees_vide() {
        userRepository.deleteAll();
        assertThat(userRepository.count()).isZero();
    }

    @Quand("je crée un utilisateur {string} avec l'email {string}")
    public void je_cree_un_utilisateur(String username, String email) {
        etat = new EtatAgentGa();
        etat.setIdEtatAgentGa(1);
        etat.setLibEtatAgentGa("active");
        AgentGa user = new AgentGa(1, "Ahmed", "ahmed@email.com",true,etat,"0000","11111111");
        userRepository.save(user);
    }

    @Alors("l'utilisateur {string} existe dans la base")
    public void l_utilisateur_existe(String username) {
        List<AgentGa> users = userRepository.findAll();
        assertThat(users).anyMatch(u -> u.getUsername().equals(username));
    }

    @Etantdonné("les utilisateurs suivants existent:")
    public void les_utilisateurs_suivants_existent(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        EtatAgentGa  etat = new EtatAgentGa();
        etat.setIdEtatAgentGa(1);
        etat.setLibEtatAgentGa("active");
        for (Map<String, String> row : rows) {

            user = new AgentGa(1, "Ahmed", "ahmed@email.com",true,etat,"0000","11111111");

        }
            AgentGa user = new AgentGa(
                    Integer.parseInt(row.get("id")),
                    row.get("username"),
                    row.get("email"),
                    row.("isIsie"),
        row.("etatAgentGa") ,
        row.("matricule"),
        row.("cinAgentGa")
            );
            userRepository.save(user);
        }
    }

    @Quand("je demande la liste des utilisateurs")
    public void je_demande_la_liste() {
        userList = userRepository.findAll();
    }

    @Alors("je reçois {int} utilisateurs")
    public void je_recois_utilisateurs(int count) {
        assertThat(userList).hasSize(count);
    }

    @Etantdonné("un utilisateur {string} existe avec l'ID {int}")
    public void un_utilisateur_existe_avec_id(String username, int id) {
        userRepository.save(new AgentGa(id, username, username.toLowerCase() +
                "@email.com"));
    }

    @Quand("je supprime l'utilisateur avec l'ID {int}")
    public void je_supprime_utilisateur(int id) {
        userRepository.deleteById(id);
    }

    @Alors("l'utilisateur avec l'ID {int} n'existe plus")
    public void utilisateur_n_existe_plus(int id) {
        assertThat(userRepository.existsById(id)).isFalse();
    }*/