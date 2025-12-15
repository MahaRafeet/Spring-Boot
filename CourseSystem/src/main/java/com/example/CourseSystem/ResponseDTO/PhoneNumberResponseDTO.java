package com.example.CourseSystem.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PhoneNumberResponseDTO {
    private Integer id;
    private String  number;
    private String countryCode;
}
