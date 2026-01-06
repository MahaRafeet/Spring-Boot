package com.example.CourseSystem.RequestDTO;

import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDTO {
    private String courseName;
    private Integer hours;
    private Integer instructorId;
    private Integer departmentId;



    public static void validateCourseRequest(CourseRequestDTO courseRequestDTO) throws Exception{
        if(Helper.isNull(courseRequestDTO.getCourseName()) || Helper.isBlank(courseRequestDTO.getCourseName())){
            throw new Exception(Constans.BAD_REQUEST);
        }
        else if(Helper.isNull(courseRequestDTO.getHours()) || courseRequestDTO.getHours()<=0){
            throw new Exception(Constans.BAD_REQUEST);
        }
        else if(Helper.isNull(courseRequestDTO.getInstructorId())|| courseRequestDTO.getInstructorId()<=0){
            throw new Exception(Constans.BAD_REQUEST);
        }
        else if(Helper.isNull(courseRequestDTO.getDepartmentId()) || courseRequestDTO.getDepartmentId()<=0){
            throw new Exception(Constans.BAD_REQUEST);
        }


    }

}
