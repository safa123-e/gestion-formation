package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.model.ProfilGa;
import com.demo.model.ProfilPrivilegeGa;
import com.demo.model.ProfilPrivilegeGaPK;

;

public interface ProfilPrivilegeGaRepository
		extends JpaRepository<ProfilPrivilegeGa, ProfilPrivilegeGaPK>, JpaSpecificationExecutor<ProfilPrivilegeGa> {

	List<ProfilPrivilegeGa> findByProfilGaAndEtatPrivProfilGa(ProfilGa profilGa, Integer etatPrivProfilGa);

}
