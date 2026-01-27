package com.demo.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "privilege_ga")
@NamedQuery(name = "PrivilegeGa.findAll", query = "SELECT p FROM PrivilegeGa p")
public class PrivilegeGa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_privilege_ga")
	private String idPrivilegeGa;

	@Column(name = "lib_privilege_ar_ga")
	private String libPrivilegeArGa;

	@Column(name = "lib_privilege_fr_ga")
	private String libPrivilegeFrGa;

	@Column(name = "desc_privilege_ga")
	private String descPrivilegeGa;

 
}
