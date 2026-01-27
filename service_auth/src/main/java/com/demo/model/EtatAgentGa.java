package com.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "etat_agent_ga")
@NamedQuery(name = "EtatAgentGa.findAll", query = "SELECT e FROM EtatAgentGa e")
public class EtatAgentGa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_etat_agent_ga")
	private Integer idEtatAgentGa;

	@Column(name = "lib_etat_agent_ga")
	private String libEtatAgentGa;
 
}
