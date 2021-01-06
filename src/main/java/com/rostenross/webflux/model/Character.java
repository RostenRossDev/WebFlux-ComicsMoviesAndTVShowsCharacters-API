package com.rostenross.webflux.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Character{
    
	@Id
	private String id;
	@NotNull
	private String fullName;
	private String imgUri;
	@NotNull
	private String description;
	private List<Character> family;
	private List<Character> master;
	
	
	public Character(String fullName, String description) {
		this.fullName=fullName;
		this.description=description;
	}
	public Character(String fullName, String imgUri, String description) {
		this.fullName = fullName;
		this.imgUri = imgUri;
		this.description = description;

	}
	public Character(String id, Character charac) {
		this.id=id;
		this.fullName=charac.getFullName();
		this.imgUri=charac.getImgUri();
		this.description=charac.getDescription();
	}
	public Character() {}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getImgUri() {
		return imgUri;
	}
	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Character> getFamily() {
		return family;
	}
	public void setFamily(List<Character> family) {
		this.family = family;
	}
	public List<Character> getMaster() {
		return master;
	}
	public void setMaster(List<Character> master) {
		this.master = master;
	}
	
}
