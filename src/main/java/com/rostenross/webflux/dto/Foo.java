package com.rostenross.webflux.dto;

public class Foo {
	private long id;
	private String name;
	
	public Foo() {
		super();
	}
	
	public Foo(final long id, final String name) {
		super();
		this.id=id;
		this.name=name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
