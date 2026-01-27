package com.demo.dto;

public class RegisterUserDto {
	private String email;

	private String password;

	private String fullName;

	public String getEmail() {
		return this.email;
	}

	public String setEmail(String email) {
		this.email = email;
		return this.email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}