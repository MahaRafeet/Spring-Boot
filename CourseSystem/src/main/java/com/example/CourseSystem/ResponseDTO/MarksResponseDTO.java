package com.example.CourseSystem.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarksResponseDTO {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Double score;
}
