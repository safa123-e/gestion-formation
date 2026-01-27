package servicetest;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;

import tn.isie.auth.model.AgentGa;
import tn.isie.auth.model.EtatAgentGa;
import tn.isie.auth.repository.AgentGaRepository;
import tn.isie.auth.services.AgentGaService;

import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires de UserService")
class AgentServiceTest {
 @Mock
private AgentGaRepository agentGaRepository;
 @InjectMocks
 private AgentGaService userService;
 private AgentGa user;
 private EtatAgentGa etat ;
 @BeforeEach
 void setUp() {
	  etat = new EtatAgentGa();
	 etat.setIdEtatAgentGa(1);
	 etat.setLibEtatAgentGa("active");
 user = new AgentGa(1, "Ahmed", "ahmed@email.com",true,etat,"0000","11111111");
 }
 @Test
 @DisplayName("Doit sauvegarder un utilisateur avec succès")
 void saveUser_Success() {
 // Arrange
 when(agentGaRepository.save(any(AgentGa.class))).thenReturn(user);
 // Act
 AgentGa result = userService.save(user);
 // Assert
 assertThat(result).isNotNull();
 assertThat(result.getUsername()).isEqualTo("11111111");
 verify(agentGaRepository, times(1)).save(user);
 }
 @Test
 @DisplayName("Doit retourner tous les utilisateurs")
 void getAllUsers_Success() {
     // Arrange
     List<AgentGa> users = Arrays.asList(
         new AgentGa(1, "Ahmed", "ahmed@email.com", true, etat,"0000","11111111"),
         new AgentGa(2, "Fatma", "fatma@email.com", true, etat,"0000","22222222")
     );

     when(agentGaRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(users);

     // Act
     List<AgentGa> result = userService.getAllAgentIsieActif(true, 1);

     // Assert
     assertThat(result).hasSize(2);
     assertThat(result.get(0).getUsername()).isEqualTo("11111111");
 }

 @Test
 @DisplayName("Doit trouver un utilisateur par email")
 void findAgentGaByEmailAgentGa_Found() {
 // Arrange
 when(agentGaRepository.findByEmailAgentGa("ahmed@email.com")).thenReturn(user);
 // Act
 AgentGa result = userService.findAgentGaByEmailAgentGa("ahmed@email.com");
 // Assert
 assertThat(result).isNotNull();
 assertThat(result.getUsername()).isEqualTo("11111111");
 }
 @Test
 @DisplayName("Doit retourner vide si utilisateur non trouvé")
 void findAgentGaByEmailAgentGa_NotFound() {
 // Arrange
 when(agentGaRepository.findByEmailAgentGa("ahmed@email.com")).thenReturn(null);
 // Act
 AgentGa result = userService.findAgentGaByEmailAgentGa("ahmed@email.com");
 // Assert
 assertThat(result).isNull();
 }
 @Test
 @DisplayName("Doit trouver un utilisateur par ID")
 void getUserById_Found() {
 // Arrange
 when(agentGaRepository.findById(1)).thenReturn(Optional.of(user));
 // Act
 AgentGa result = userService.findAgentGaById(1);
 // Assert
 assertThat(result).isNotNull();
 assertThat(result.getUsername()).isEqualTo("11111111");
 }

 @Test
 @DisplayName("Doit lancer exception si utilisateur non trouvé par ID")
 void getUserById_NotFound() {
     when(agentGaRepository.findById(99)).thenReturn(Optional.empty());

     RuntimeException exception = assertThrows(RuntimeException.class, () -> {
         userService.findAgentGaById(99);
     });

     assertThat(exception.getMessage()).contains("Agent introuvable");
 }

 
 @Test
 @DisplayName("Doit mettre à jour un utilisateur existant")
 void updateUser_Success() {
 // Arrange
 AgentGa updatedUser = new AgentGa(1, "Ahmed Updated", "new@email.com",true,etat,"0000","11111111");
 when(agentGaRepository.save(any(AgentGa.class))).thenReturn(updatedUser);
 // Act
 AgentGa result = userService.updateProfilAgent(updatedUser);
 // Assert
 assertThat(result).isNotNull();
 assertThat(result.getUsername()).isEqualTo("11111111");
 }
 @Test
 @DisplayName("Doit retourner null si utilisateur à mettre à jour n'existe pas")
 void updateUser_NotFound() {
	 AgentGa updatedUser = new AgentGa(99, "Ahmed Updated", "new@email.com",true,etat,"0000","11111111");

 // Arrange
 // Act
	 AgentGa result = userService.updateProfilAgent(updatedUser);
 // Assert
 assertThat(result).isNull();
 verify(agentGaRepository, never()).save(result);
 }
// @Test
// @DisplayName("Doit supprimer un utilisateur")
// void deleteUser_Success() {
// // Arrange
// doNothing().when(agentGaRepository).deleteById(1);
// // Act
// userService.deleteUser(1);
// // Assert
// verify(agentGaRepository, times(1)).deleteById(1);
// }
 

}
