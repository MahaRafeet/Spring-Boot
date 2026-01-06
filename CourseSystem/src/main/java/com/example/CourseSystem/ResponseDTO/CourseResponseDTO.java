package com.example.CourseSystem.ResponseDTO;

import com.example.CourseSystem.Entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {
    private Integer id;
    private String courseName;
    private Integer hours;

    private Integer instructorId;
    private String instructorName;

    private Integer departmentId;
    private String departmentName;

    public static CourseResponseDTO convertEntityToDTO(Course course){
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setId(course.getId());
        courseResponseDTO.setCourseName(course.getCourseName());
        courseResponseDTO.setHours(course.getHours());
        if(course.getInstructor() != null) {
            courseResponseDTO.setInstructorName(course.getInstructor().getInstructorName());
            courseResponseDTO.setInstructorId(course.getInstructor().getId());
        }
        if(course.getDepartment() != null) {
            courseResponseDTO.setDepartmentName(course.getDepartment().getDepartmentName());
            courseResponseDTO.setDepartmentId(course.getDepartment().getDepId());
        }
        return courseResponseDTO;
    }
}
