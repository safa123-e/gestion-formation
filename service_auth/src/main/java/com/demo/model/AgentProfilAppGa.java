package com.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Table(name = "agent_profil_app_ga")
@NamedQuery(name = "AgentProfilAppGa.findAll", query = "SELECT a FROM AgentProfilAppGa a")
public class AgentProfilAppGa implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AgentProfilAppGaPK id;

	@MapsId("idAgentGa")
	@ManyToOne
	@JoinColumn(name = "id_agent_ga")
	private AgentGa agentGa;

	@MapsId("idProfilGa")
	@ManyToOne
	@JoinColumn(name = "id_profil_ga")
	private ProfilGa profilGa;

	@MapsId("idAppGa")
	@ManyToOne
	@JoinColumn(name = "id_app_ga")
	private ApplicationGa applicationGa;

	@Column(name = "etat_agent_prof_app_ga")
	private Integer etatAgentProfAppGa;

	@Column(name = "id_agent_affect_ga")
	private Integer idAgentAffectGa;

	

}
