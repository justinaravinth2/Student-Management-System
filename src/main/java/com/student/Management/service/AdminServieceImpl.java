package com.student.Management.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.Management.dto.CourseDTO;
import com.student.Management.dto.StudentDTO;
import com.student.Management.entity.CourseVO;
import com.student.Management.entity.StudentAddressVO;
import com.student.Management.entity.StudentVO;
import com.student.Management.exception.ApplicationException;
import com.student.Management.repo.CourseRepo;
import com.student.Management.repo.StudentAddressRepo;
import com.student.Management.repo.StudentRepo;

@Service
public class AdminServieceImpl implements AdminService {

	public static final Logger LOGGER = LoggerFactory.getLogger(AdminServieceImpl.class);

	@Autowired
	StudentRepo studentRepository;

	@Autowired
	StudentAddressRepo studentAddressRepo;

	@Autowired
	CourseRepo courseRepo;

	@Override
	public Map<String, Object> addStudent(StudentDTO studentDTO) throws ApplicationException {
		StudentVO student = new StudentVO();
		String message = null;

		if (studentRepository.existsByStudentCodeIgnoreCase(studentDTO.getStudentCode()))
			throw new ApplicationException("Student Code Already Exist");

		message = "Student Created Successfully";
		student.setStudentCode(studentDTO.getStudentCode());
		mapStudentDTOtoStudentVO(studentDTO, student);
		studentRepository.save(student);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("studentVO", student);
		return response;
	}

	private void mapStudentDTOtoStudentVO(StudentDTO studentDTO, StudentVO student) {

		student.setName(studentDTO.getName());
		student.setDateOfBirth(studentDTO.getDateOfBirth());
		student.setGender(studentDTO.getGender());
		student.setEmail(studentDTO.getEmail());
		student.setMobileNumber(studentDTO.getMobileNumber());
		student.setParentsName(studentDTO.getParentsName());
		List<StudentAddressVO> studentAddress = studentDTO.getAddresses().stream().map(addressDTO -> {
			StudentAddressVO studentAddressVO = new StudentAddressVO();
			studentAddressVO.setAddressType(addressDTO.getAddressType());
			studentAddressVO.setArea(addressDTO.getArea());
			studentAddressVO.setDistrict(addressDTO.getDistrict());
			studentAddressVO.setPincode(addressDTO.getPincode());
			studentAddressVO.setState(addressDTO.getState());
			studentAddressVO.setStudent(student);
			return studentAddressVO;
		}).collect(Collectors.toList());
		student.setAddresses(studentAddress);

	}

	@Override
	public List<StudentVO> getStudentByName(String name) {
		return studentRepository.findAll().stream().filter(student -> student.getName().equalsIgnoreCase(name))
				.collect(Collectors.toList());
	}

	@Override
	public boolean studentValidation(String studentCode, String dob) {

		Optional<StudentVO> student = studentRepository.findByStudentCodeIgnoreCase(studentCode);
		if (student.isPresent() && student.get().getDateOfBirth().toString().equals(dob)) {
			return true;
		}
		return false;

	}

	@Override
	public Map<String, Object> addCourseToStudent(CourseDTO courseDTO) throws ApplicationException {
		CourseVO courseVO = new CourseVO();
		String message = null;

		if (courseRepo.existsByCourseNameIgnoreCase(courseDTO.getCourseName()))
			throw new ApplicationException("Couser Name Already Exist");

		message = "Course Created Successfully";
		courseVO.setCourseName(courseDTO.getCourseName());
		mapCourseDTOtoCourseVO(courseDTO, courseVO);

		courseRepo.save(courseVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("courseVO", courseVO);
		return response;
	}

	private void mapCourseDTOtoCourseVO(CourseDTO courseDTO, CourseVO courseVO) {
		courseVO.setCourseType(courseDTO.getCourseType());
		courseVO.setDescription(courseDTO.getDescription());
		courseVO.setDuration(courseDTO.getDuration());
	}

	@Override
	public StudentVO assignCoursesToStudent(String studentCode, List<Long> courseIds) {
		StudentVO student = studentRepository.findByStudentCodeIgnoreCase(studentCode)
				.orElseThrow(() -> new RuntimeException("Student not found"));

		Set<CourseVO> courses = courseRepo.findAllById(courseIds).stream().collect(Collectors.toSet());

		student.getCourses().addAll(courses);
		return studentRepository.save(student);
	}

	@Override
	public List<StudentVO> getStudentsByCourse(String courseName) {

		CourseVO courseVO = courseRepo.findByCourseNameIgnoreCase(courseName);

		List<StudentVO> studentVOs = studentRepository.findByCourses(courseVO);

		return studentVOs;
	}

}
