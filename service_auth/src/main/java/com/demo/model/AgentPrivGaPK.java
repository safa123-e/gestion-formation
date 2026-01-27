package com.demo.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AgentPrivGaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_agent_ga", insertable = false, updatable = false)
	private Integer idAgentGa;

	@Column(name = "id_privilege_ga", insertable = false, updatable = false)
	private String idPrivilegeGa;

	@Override
	public int hashCode() {
		return Objects.hash(idAgentGa, idPrivilegeGa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AgentPrivGaPK)) {
			return false;
		}
		AgentPrivGaPK other = (AgentPrivGaPK) obj;
		return Objects.equals(idAgentGa, other.idAgentGa) && Objects.equals(idPrivilegeGa, other.idPrivilegeGa);
	}

}
