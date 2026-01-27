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
@Table(name = "service_ga")
@NamedQuery(name = "ServiceGa.findAll", query = "SELECT s FROM ServiceGa s")
public class ServiceGa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_service_ga")
	private Integer idServiceGt;

	@Column(name = "lib_service_ga")
	private String libServiceGa;

	@Column(name = "desc_service_ga")
	private String descServiceGa;

	@Column(name = "icon_service_ga")
	private String iconServiceGa;

	@Column(name = "color_service_ga")
	private String colorServiceGa;

 

}
