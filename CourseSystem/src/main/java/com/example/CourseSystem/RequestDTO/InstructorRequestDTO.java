package com.example.CourseSystem.RequestDTO;

import com.example.CourseSystem.Entity.Department;
import com.example.CourseSystem.Entity.Instructor;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRequestDTO {
    private String instructorName;
    private String email ;
    private Integer departmentId;

    public static void validateInstructorRequest(InstructorRequestDTO request)throws Exception{
        if(Helper.isNull(request.getEmail()) || Helper.isBlank(request.getEmail())|| request.getEmail().isEmpty()){
            throw new Exception(Constans.BAD_REQUEST);

        }
        if(Helper.isNull(request.getInstructorName()) || Helper.isBlank(request.getInstructorName()) || request.getInstructorName().isEmpty()){
            throw new Exception(Constans.BAD_REQUEST);
        }
        if(Helper.isNull(request.getDepartmentId()) || request.getDepartmentId()<=0){
            throw new Exception(Constans.BAD_REQUEST);
        }
    }

}
