package com.student.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
	
    private String courseName;
    private String description;
    private String courseType;
    private int duration;

}
