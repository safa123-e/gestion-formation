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
@Table(name = "grade_agent_ga")
@NamedQuery(name = "GradeAgentGa.findAll", query = "SELECT g FROM GradeAgentGa g")
public class GradeAgentGa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_grade_agent_ga")
	private Integer idGradeAgentGa;

	@Column(name = "lib_grade_agent_ga")
	private String libGradeAgentGa;

 

}
