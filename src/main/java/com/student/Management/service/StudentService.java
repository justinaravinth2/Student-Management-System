package com.student.Management.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.student.Management.dto.StudentUpdateDTO;
import com.student.Management.entity.CourseVO;

@Service
public interface StudentService {
	
	
	

	boolean updateStudentProfile(String studentCode, LocalDate dateOfBirth, StudentUpdateDTO updateDTO);

	Set<CourseVO> getStudentCourses(String studentCode, LocalDate dob);

	boolean leaveCourse(String studentCode, LocalDate dob, Long courseId);

	

}
