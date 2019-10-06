package com.jramos.pattern.entity;

public class Person {

	private String name;
	private String lastName;
	private Integer age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Person(String name, String lastName, Integer age) {
		this.name = name;
		this.lastName = lastName;
		this.age = age;
	}
}


