package com.mardeveloper.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mardeveloper.demo.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	public Course findByName(String courseName);
	
}