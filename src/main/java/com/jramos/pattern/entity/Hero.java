package com.jramos.pattern.entity;

public class Hero extends Person {

	private String alias;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Hero(String name, String lastName, Integer age, String alias) {
		super(name, lastName, age);
		this.alias = alias;
	}
}
