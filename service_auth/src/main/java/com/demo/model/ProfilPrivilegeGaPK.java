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
public class ProfilPrivilegeGaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_profil_ga", insertable = false, updatable = false)
	private String idProfilGa;

	@Column(name = "id_privilege_ga", insertable = false, updatable = false)
	private String idPrivilegeGa;

 

	@Override
	public int hashCode() {
		return Objects.hash(idPrivilegeGa, idProfilGa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ProfilPrivilegeGaPK)) {
			return false;
		}
		ProfilPrivilegeGaPK other = (ProfilPrivilegeGaPK) obj;
		return Objects.equals(idPrivilegeGa, other.idPrivilegeGa) && Objects.equals(idProfilGa, other.idProfilGa);
	}

}
