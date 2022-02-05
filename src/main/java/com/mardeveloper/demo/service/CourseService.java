package com.mardeveloper.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mardeveloper.demo.dto.CourseDto;
import com.mardeveloper.demo.entity.Course;
import com.mardeveloper.demo.entity.Student;
import com.mardeveloper.demo.repository.CourseRepository;
import com.mardeveloper.demo.repository.StudentRepository;

@Service
public class CourseService {
	
	@Resource
	private StudentRepository studentRepository;

	@Resource
	private CourseRepository courseRepository;

	@Transactional
	public CourseDto addCourse(CourseDto courseDto) {
		Course course = new Course();
		mapDtoToEntity(courseDto, course);
		Course savedCourse = courseRepository.save(course);
		return mapEntityToDto(savedCourse);
	}

	public List<CourseDto> getAllCourses() {
		List<CourseDto> courseDtos = new ArrayList<>();
		List<Course> courses = courseRepository.findAll();
		courses.stream().forEach(course -> {
			CourseDto courseDto = mapEntityToDto(course);
			courseDtos.add(courseDto);
		});
		return courseDtos;
	}

	@Transactional
	public CourseDto updateCourse(Integer id, CourseDto courseDto) {
		Course crs = courseRepository.getOne(id);
		crs.getStudents().clear();
		mapDtoToEntity(courseDto, crs);
		Course course = courseRepository.save(crs);
		return mapEntityToDto(course);
	}

	@Transactional
	public String deleteCourse(Integer id) {
		Optional<Course> course = courseRepository.findById(id);
		// Remove the related students from course entity.
		if(course.isPresent()) {
			course.get().removeStudents();
			courseRepository.deleteById(course.get().getId());
			return "Course with id: " + id + " deleted successfully!";
		}
		return null;
	}

	private void mapDtoToEntity(CourseDto courseDto, Course course) {
		course.setName(courseDto.getName());
		if (null == course.getStudents()) {
			course.setStudents(new HashSet<>());
		}
		courseDto.getStudents().stream().forEach(studentName -> {
			Student student = studentRepository.findByName(studentName);
			if (null == student) {
				student = new Student();
				student.setCourses(new HashSet<>());
			}
			student.setName(studentName);
			student.addCourse(course);
		});
	}

	private CourseDto mapEntityToDto(Course course) {
		CourseDto responseDto = new CourseDto();
		responseDto.setName(course.getName());
		responseDto.setId(course.getId());
		responseDto.setStudents(course.getStudents().stream().map(Student::getName).collect(Collectors.toSet()));
		return responseDto;
	}

}
