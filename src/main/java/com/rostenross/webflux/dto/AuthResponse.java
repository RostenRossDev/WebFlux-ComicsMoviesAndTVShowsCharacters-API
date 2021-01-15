package com.rostenross.webflux.dto;


public class AuthResponse {

	private String jwt;
	
	
	
	public AuthResponse(String jwt) {
		this.jwt=jwt;
	}
	
	public String getJwt() {
		return this.jwt;
	}
	
	public void setJwt(String jwt) {
		this.jwt=jwt;
	}
}
