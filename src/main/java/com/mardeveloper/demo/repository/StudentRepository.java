package com.mardeveloper.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mardeveloper.demo.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	Student findByName(String studentName);

}