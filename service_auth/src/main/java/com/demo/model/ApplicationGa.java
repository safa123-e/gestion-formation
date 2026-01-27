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
@Table(name = "application_ga")
@NamedQuery(name = "ApplicationGa.findAll", query = "SELECT p FROM ApplicationGa p")
public class ApplicationGa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_app_ga")
	private Integer idAppGa;

	@Column(name = "lib_app_ga")
	private String libAppGa;

	@Column(name = "desc_app_ga")
	private String descAppGa;

	@Column(name = "etat_app_ga")
	private Integer etatAppGa;

	 

}
