package com.demo.model;

import java.io.Serializable;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "agent_priv_ga")
@NamedQuery(name = "AgentPrivGa.findAll", query = "SELECT a FROM AgentPrivGa a")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@ToString(exclude = {"agentGa", "privilegeGa"}) // évite boucles/lazy
public class AgentPrivGa implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AgentPrivGaPK id;

    @MapsId("idAgentGa")
    @ManyToOne
    @JoinColumn(name = "id_agent_ga")
    private AgentGa agentGa;

    @MapsId("idPrivilegeGa")
    @ManyToOne
    @JoinColumn(name = "id_privilege_ga")
    private PrivilegeGa privilegeGa;

    @Column(name = "etat_agent_priv_ga")
    private Integer etatAgentPrivGa;

    @Column(name = "id_user_affect_priv_ga")
    private Integer idUserAffectPrivGa;
}
