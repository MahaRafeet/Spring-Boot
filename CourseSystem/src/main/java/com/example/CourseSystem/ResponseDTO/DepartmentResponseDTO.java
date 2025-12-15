package com.example.CourseSystem.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponseDTO {
        private Integer depId;
        private String departmentName;
        private Boolean isActive;
        private LocalDate createdDate;
        private LocalDate updatedDate;
    private List<String> instructorNames;
    private List<String> courseNames;

}
