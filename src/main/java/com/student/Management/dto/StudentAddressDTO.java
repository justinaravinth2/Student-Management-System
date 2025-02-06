package com.student.Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAddressDTO {
    private String area;
    private String state;
    private String district;
    private String pincode;
    private String addressType;
}
