package com.student.Management.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student.Management.common.CommonConstant;
import com.student.Management.common.UserConstants;
import com.student.Management.dto.ResponseDTO;
import com.student.Management.dto.StudentUpdateDTO;
import com.student.Management.entity.CourseVO;
import com.student.Management.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentControllers extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(StudentControllers.class);

	@Autowired
	StudentService studentService;

	@GetMapping("/{studentCode}/{dob}/courses")
	public ResponseEntity<ResponseDTO> getStudentCoursesdetails(@PathVariable String studentCode,
			@PathVariable String dob) {
		LocalDate dateOfBirth = LocalDate.parse(dob);
		String methodName = "getStudentCoursesdetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<CourseVO> courses = new HashSet<>();
		try {
			courses = studentService.getStudentCourses(studentCode, dateOfBirth);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Course information get successfully By Student");
			responseObjectsMap.put("coursesVO", courses);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Course information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}

	@PutMapping("/{studentCode}/{dob}/update-profile")
	public ResponseEntity<ResponseDTO> updateProfile(@PathVariable String studentCode, @PathVariable String dob,
			@RequestBody StudentUpdateDTO updateDTO) {
		LocalDate dateOfBirth = LocalDate.parse(dob);
		String methodName = "updateProfile()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			boolean isUpdated = studentService.updateStudentProfile(studentCode, dateOfBirth, updateDTO);
			if (isUpdated) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Student profile updated successfully.");
			} else {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Invalid student code or DOB.");
			}
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@DeleteMapping("/{studentCode}/{dob}/leave-course/{courseId}")
	public ResponseEntity<ResponseDTO> leaveCourse(@PathVariable String studentCode, @PathVariable String dob,
			@PathVariable Long courseId) {
		LocalDate dateOfBirth = LocalDate.parse(dob);
		String methodName = "leaveCourse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			boolean isRemoved = studentService.leaveCourse(studentCode, dateOfBirth, courseId);
			if (isRemoved) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Course removed successfully.");
			} else {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Invalid student code or DOB.");
			}
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
