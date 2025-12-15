package com.example.CourseSystem.RequestDTO;

import com.example.CourseSystem.Entity.Course;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksRequestDTO {
    private Integer studentId;
    private Double score;
    private Integer courseId;

    public static void marksRequestValidation(MarksRequestDTO requestDTO) throws Exception {
        if (Helper.isNull(requestDTO.getStudentId()) || requestDTO.getStudentId()<0) {
            throw new Exception(Constans.BAD_REQUEST);
        }
        if (Helper.isNull(requestDTO.getScore()) || requestDTO.getScore() < 0) {
            throw new Exception(Constans.BAD_REQUEST);
        }
        if (Helper.isNull(requestDTO.getCourseId()) || requestDTO.getCourseId() <= 0) {
            throw new Exception(Constans.BAD_REQUEST);
        }
    }
}
