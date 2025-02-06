package com.student.Management.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.student.Management.dto.CourseDTO;
import com.student.Management.dto.StudentDTO;
import com.student.Management.entity.StudentVO;
import com.student.Management.exception.ApplicationException;

@Service
public interface AdminService {

	Map<String, Object> addStudent(StudentDTO studentDTO) throws ApplicationException;

	List<StudentVO> getStudentByName(String studentName);

	boolean studentValidation(String studentCode, String dob);

	Map<String, Object> addCourseToStudent(CourseDTO courseDTO) throws ApplicationException;

	StudentVO assignCoursesToStudent(String studentCode, List<Long> courseIds);

	List<StudentVO> getStudentsByCourse(String courseName);

}
