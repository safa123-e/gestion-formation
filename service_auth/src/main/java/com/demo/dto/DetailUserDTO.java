package com.demo.dto;

import java.util.List;

public class DetailUserDTO {

	private String login;
	private String pwd;
	private String election;
	private String application;
	private String token;
	private String libProfil;
	private String nomPrenomAgentGa;
	private Boolean premierAcces;
	private List<PrivilageDTO> utilisPriv;
	private byte[] imageBytes;
	private String imageUrl;
	private String libLieuTravail;
	private String cinAgentGa;
	private String emailAgentGa;
	private String matAgentGa;
	private String telAgentGa;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getElection() {
		return election;
	}

	public void setElection(String election) {
		this.election = election;
	}

	public String getLibProfil() {
		return libProfil;
	}

	public void setLibProfil(String libProfil) {
		this.libProfil = libProfil;
	}

	public List<PrivilageDTO> getUtilisPriv() {
		return utilisPriv;
	}

	public void setUtilisPriv(List<PrivilageDTO> privilageDTO) {
		utilisPriv = privilageDTO;
	}

	public Boolean getPremierAcces() {
		return premierAcces;
	}

	public String getNomPrenomAgentGa() {
		return nomPrenomAgentGa;
	}

	public void setNomPrenomAgentGa(String nomPrenomAgentGa) {
		this.nomPrenomAgentGa = nomPrenomAgentGa;
	}

	public void setPremierAcces(Boolean premierAcces) {
		this.premierAcces = premierAcces;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLibLieuTravail() {
		return libLieuTravail;
	}

	public void setLibLieuTravail(String libLieuTravail) {
		this.libLieuTravail = libLieuTravail;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCinAgentGa() {
		return cinAgentGa;
	}

	public void setCinAgentGa(String cinAgentGa) {
		this.cinAgentGa = cinAgentGa;
	}

	public String getEmailAgentGa() {
		return emailAgentGa;
	}

	public void setEmailAgentGa(String emailAgentGa) {
		this.emailAgentGa = emailAgentGa;
	}

	public String getMatAgentGa() {
		return matAgentGa;
	}

	public void setMatAgentGa(String matAgentGa) {
		this.matAgentGa = matAgentGa;
	}

	public String getTelAgentGa() {
		return telAgentGa;
	}

	public void setTelAgentGa(String telAgentGa) {
		this.telAgentGa = telAgentGa;
	}

}
