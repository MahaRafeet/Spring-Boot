package com.example.CourseSystem.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorResponseDTO {
    private Integer id;
    private String instructorName;
    private String email ;
    private Boolean isActive;
    private Integer departmentId;
    private String departmentName;

}
