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

@Table(name = "profil_privilege_ga")
@NamedQuery(name = "ProfilPrivilegeGa.findAll", query = "SELECT p FROM ProfilPrivilegeGa p")
public class ProfilPrivilegeGa implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProfilPrivilegeGaPK id;

	@MapsId("idProfilGa")
	@ManyToOne
	@JoinColumn(name = "id_profil_ga")
	private ProfilGa profilGa;

	@MapsId("idPrivilegeGa")
	@ManyToOne
	@JoinColumn(name = "id_privilege_ga")
	private PrivilegeGa privilegeGa;

	@Column(name = "etat_priv_profil_ga")
	private Integer etatPrivProfilGa;

 
}