package com.example.CourseSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String courseName;
    private Integer hours;


    // Course → Marks : OneToMany
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Marks> marks;
    //Course ↔ Instructor : @OneToOne
    @OneToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor;
    //Course → Department : @ManyToOne
    @ManyToOne
    @JoinColumn(name = "depId", referencedColumnName = "depId")
    private Department department;

    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;


}
