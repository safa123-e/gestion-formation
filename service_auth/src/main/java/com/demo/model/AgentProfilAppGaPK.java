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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class AgentProfilAppGaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_agent_ga", insertable = false, updatable = false)
	private Integer idAgentGa;

	@Column(name = "id_profil_ga", insertable = false, updatable = false)
	private String idProfilGa;

	@Column(name = "id_app_ga", insertable = false, updatable = false)
	private Integer idAppGa;

 

	@Override
	public int hashCode() {
		return Objects.hash(idAgentGa, idAppGa, idProfilGa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AgentProfilAppGaPK)) {
			return false;
		}
		AgentProfilAppGaPK other = (AgentProfilAppGaPK) obj;
		return Objects.equals(idAgentGa, other.idAgentGa) && Objects.equals(idAppGa, other.idAppGa)
				&& Objects.equals(idProfilGa, other.idProfilGa);
	}

}
