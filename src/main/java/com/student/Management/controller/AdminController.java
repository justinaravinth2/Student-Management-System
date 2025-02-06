package com.student.Management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.student.Management.common.CommonConstant;
import com.student.Management.common.UserConstants;
import com.student.Management.dto.CourseDTO;
import com.student.Management.dto.ResponseDTO;
import com.student.Management.dto.StudentDTO;
import com.student.Management.entity.StudentVO;
import com.student.Management.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	AdminService adminService;
	
	@PostMapping("/addStudent")
	public ResponseEntity<ResponseDTO> addStudent(@RequestBody StudentDTO studentDTO) {
		String methodName = "addStudent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> studentVO = adminService.addStudent(studentDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, studentVO.get("message"));
			responseObjectsMap.put("studentVO", studentVO.get("studentVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getStudentsByName")
	public ResponseEntity<ResponseDTO> getStudentsByName(@RequestParam String studentName) {
		String methodName = "getStudentsByName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StudentVO> studentVO = new ArrayList<>();
		try {
			studentVO = adminService.getStudentByName(studentName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Students information get successfully By Name");
			responseObjectsMap.put("studentVO", studentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Students information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	
	
	
	@PostMapping("/addCourseDetails")
	public ResponseEntity<ResponseDTO> addCourses(@RequestBody CourseDTO courseDTO) {
		String methodName = "addCourses()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> courseVO = adminService.addCourseToStudent(courseDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, courseVO.get("message"));
			responseObjectsMap.put("courseVO", courseVO.get("courseVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/{studentCode}/assign-courses")
	public ResponseEntity<ResponseDTO> assignCourse(@PathVariable  String studentCode, 
            @RequestBody List<Long> courseIds) {
		String methodName = "assignCourse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StudentVO studentVO = adminService.assignCoursesToStudent(studentCode, courseIds);
			responseObjectsMap.put("studentVO", studentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getStudentsByCourse")
	public ResponseEntity<ResponseDTO> getStudentsByCourse(@RequestParam String courseName) {
		String methodName = "getStudentsByName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StudentVO> studentVO = new ArrayList<>();
		try {
			studentVO = adminService.getStudentsByCourse(courseName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Students information get successfully By Course");
			responseObjectsMap.put("studentVO", studentVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Students information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);

	}
	

}
