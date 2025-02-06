package com.student.Management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.student.Management.dto.StudentUpdateDTO;
import com.student.Management.entity.CourseVO;
import com.student.Management.entity.StudentAddressVO;
import com.student.Management.entity.StudentVO;
import com.student.Management.repo.CourseRepo;
import com.student.Management.repo.StudentAddressRepo;
import com.student.Management.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	StudentRepo studentRepository;

	@Autowired
	StudentAddressRepo studentAddressRepo;
	
	@Autowired
	CourseRepo courseRepo;

	@Override
	public boolean updateStudentProfile(String studentCode, LocalDate dob, StudentUpdateDTO updateDTO) {
        Optional<StudentVO> optionalStudent = validateStudent(studentCode, dob);
        
        if (optionalStudent.isPresent()) {
            StudentVO student = optionalStudent.get();
            student.setEmail(updateDTO.getEmail());
            student.setMobileNumber(updateDTO.getMobileNumber());
            student.setParentsName(updateDTO.getParentsName());
            List<StudentAddressVO> studentAddress = updateDTO.getAddresses().stream().map(addressDTO -> {
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
            
            studentRepository.save(student);
            return true;
        }
        return false;
    }
	
	public Optional<StudentVO> validateStudent(String studentCode, LocalDate dob) {
        return studentRepository.findByStudentCodeIgnoreCase(studentCode)
                .filter(student -> student.getDateOfBirth().equals(dob));
    }

	@Override
	public Set<CourseVO> getStudentCourses(String studentCode, LocalDate dob) {
        return validateStudent(studentCode, dob)
                .map(StudentVO::getCourses).orElseThrow(null);
    }
	
	@Override
	public boolean leaveCourse(String studentCode, LocalDate dob, Long courseId) {
        Optional<StudentVO> optionalStudent = validateStudent(studentCode, dob);
        
        if (optionalStudent.isPresent()) {
            StudentVO student = optionalStudent.get();
            boolean removed = student.getCourses().removeIf(course -> course.getId().equals(courseId));
            if (!removed) {
                throw new RuntimeException("Course with ID " + courseId + " not found for this student.");
            }
            studentRepository.save(student);
            return true;
        }
        throw new RuntimeException("Invalid student code or DOB.");
    }
	
}
