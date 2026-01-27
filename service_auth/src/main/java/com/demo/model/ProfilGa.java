package com.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "profil_ga")
@NamedQuery(name = "ProfilGa.findAll", query = "SELECT p FROM ProfilGa p")
public class ProfilGa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_profil_ga")
	private String idProfilGa;

	@Column(name = "lib_profil_ga_ar")
	private String libProfilGaAr;

	@Column(name = "lib_profil_ga_fr")
	private String libProfilGaFr;

	@Column(name = "desc_profil_ga")
	private String descProfilGa;

	@Column(name = "id_etat_profil_ga")
	private Integer idEtatProfilGa;

	@Column(name = "update_profil_ga")
	private Integer updateProfilGa;

	@Column(name = "all_profil_ga")
	private Integer allProfilGa;

	@ManyToOne
	@JoinColumn(name = "id_app_ga")
	private ApplicationGa applicationGa;

 
}
