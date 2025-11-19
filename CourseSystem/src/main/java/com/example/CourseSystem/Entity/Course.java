package com.example.CourseSystem.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class Course {
    private Integer id;
    private String name;
    private String instructor;
    private Integer hours;

    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;
}
