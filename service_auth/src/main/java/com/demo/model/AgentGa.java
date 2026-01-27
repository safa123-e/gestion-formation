package com.demo.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agent_ga")
@NamedQuery(name = "AgentGa.findAll", query = "SELECT a FROM AgentGa a")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class AgentGa implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agent_ga")
    private Integer idAgentGa;

    @Column(name = "cin_agent_ga")
    private String cinAgentGa;

    @Column(name = "first_access_agent_ga")
    private Boolean firstAccessAgentGa;

    @Column(name = "agent_has_account_ga")
    private Boolean agentHasAccountGa;

    @Column(name = "nom_prenom_agent_ga")
    private String nomPrenomAgentGa;

    @Column(name = "date_crea_agent_ga")
    private LocalDateTime dateCreaAgentGa;

    @Column(name = "email_agent_ga")
    private String emailAgentGa;

    @Column(name = "mat_agent_ga")
    private String matAgentGa;

    @Column(name = "tel_agent_ga")
    private String telAgentGa;

    @Column(name = "tel_fix_agent_ga")
    private String telFixAgentGa;

    @Column(name = "id_user_agent_ga")
    private Integer idUserAgentGa;

    @Column(name = "is_isie_ga")
    private Boolean isIsieGa;

    @Column(name = "is_responsable_ga")
    private Boolean isResponsableGa;

    @ManyToOne
    @JoinColumn(name = "id_etat_agent_ga")
    private EtatAgentGa etatAgentGa;

    @Column(name = "pwd_agent_ga")
    private String pwdAgentGa;

    @Column(name = "type_agent_ga")
    private Integer typeAgentGa;

    @ManyToOne
    @JoinColumn(name = "id_grade_agent_ga")
    private GradeAgentGa gradeAgentGa;

    @ManyToOne
    @JoinColumn(name = "id_service_ga")
    private ServiceGa serviceGa;

    @Column(name = "url_image_agent_ga")
    @ToString.Include
    private String imageUrl;

    @Transient
    private byte[] imageBytes;

    // ===== UserDetails =====
    @Override
    public String getUsername() {
        return cinAgentGa;
    }

    @Override
    public String getPassword() {
        return pwdAgentGa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // Si tu veux garder EXACTEMENT ton toString actuel :
    // (sinon supprime cette méthode et garde @ToString)
    @Override
    public String toString() {
        return "AgentGa [imageUrl=" + imageUrl + ", imageBytes=" + Arrays.toString(imageBytes) + "]";
    }
    public AgentGa(int id, String nom, String email, boolean isIsieGa, EtatAgentGa etatAgentGa,String matricule,String  cinAgentGa) {
        this.idAgentGa = id;
        this.nomPrenomAgentGa = nom;
        this.emailAgentGa = email;
        this.etatAgentGa=etatAgentGa;
        this.isIsieGa=isIsieGa;
        this.matAgentGa=matricule;
        this.cinAgentGa=cinAgentGa;
    }
}
