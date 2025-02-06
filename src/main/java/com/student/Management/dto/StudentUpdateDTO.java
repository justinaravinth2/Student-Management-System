package com.student.Management.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateDTO {

	private String email;

	private String mobileNumber;

	private String parentsName;

	private List<StudentAddressDTO> addresses;

}
