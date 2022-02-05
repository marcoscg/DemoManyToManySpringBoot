package com.mardeveloper.demo.dto;

import java.util.HashSet;
import java.util.Set;

public class CourseDto {
	
	private Integer id;
	private String name;
	
	Set<String> students = new HashSet<>();

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

	public Set<String> getStudents() {
		return students;
	}

	public void setStudents(Set<String> students) {
		this.students = students;
	}	
	
}
