package com.mardeveloper.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mardeveloper.demo.dto.StudentDto;
import com.mardeveloper.demo.entity.Course;
import com.mardeveloper.demo.entity.Student;
import com.mardeveloper.demo.repository.CourseRepository;
import com.mardeveloper.demo.repository.StudentRepository;

@Service
public class StudentService {

	@Resource
	private StudentRepository studentRepository;

	@Resource
	private CourseRepository courseRepository;

	@Transactional
	public StudentDto addStudent(StudentDto studentDto) {
		Student student = new Student();
		mapDtoToEntity(studentDto, student);
		Student savedStudent = studentRepository.save(student);
		return mapEntityToDto(savedStudent);
	}

	public List<StudentDto> getAllStudents() {
		List<StudentDto> studentDtos = new ArrayList<>();
		List<Student> students = studentRepository.findAll();
		students.stream().forEach(student -> {
			StudentDto studentDto = mapEntityToDto(student);
			studentDtos.add(studentDto);
		});
		return studentDtos;
	}

	@Transactional
	public StudentDto updateStudent(Integer id, StudentDto studentDto) {
		Student std = studentRepository.getOne(id);
		std.getCourses().clear();
		mapDtoToEntity(studentDto, std);
		Student student = studentRepository.save(std);
		return mapEntityToDto(student);
	}

	public String deleteStudent(Integer studentId) {
		
		Optional<Student> student = studentRepository.findById(studentId);
		//Remove the related courses from student entity.
		if(student.isPresent()) {
			student.get().removeCourses();
			studentRepository.deleteById(student.get().getId());
			return "Student with id: " + studentId + " deleted successfully!";
		}
		return null;
	}

	private void mapDtoToEntity(StudentDto studentDto, Student student) {
		student.setName(studentDto.getName());
		if (null == student.getCourses()) {
			student.setCourses(new HashSet<>());
		}
		studentDto.getCourses().stream().forEach(courseName -> {
			Course course = courseRepository.findByName(courseName);
			if (null == course) {
				course = new Course();
				course.setStudents(new HashSet<>());
			}
			course.setName(courseName);
			student.addCourse(course);
		});
	}

	private StudentDto mapEntityToDto(Student student) {
		StudentDto responseDto = new StudentDto();
		responseDto.setName(student.getName());
		responseDto.setId(student.getId());
		responseDto.setCourses(student.getCourses().stream().map(Course::getName).collect(Collectors.toSet()));
		return responseDto;
	}
}
