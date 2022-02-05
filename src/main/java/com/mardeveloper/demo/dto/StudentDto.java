package com.mardeveloper.demo.dto;

import java.util.HashSet;
import java.util.Set;

public class StudentDto {
	
	private Integer id;
	private String name;
	
	private Set<String> courses = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getCourses() {
		return courses;
	}

	public void setCourses(Set<String> courses) {
		this.courses = courses;
	}

}
