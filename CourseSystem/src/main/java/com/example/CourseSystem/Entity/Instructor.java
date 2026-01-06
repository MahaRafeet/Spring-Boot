package com.example.CourseSystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;
    private String instructorName ;
    private String email ;
    private Boolean isActive ;
    //Course ↔ Instructor : @OneToOne
    @OneToOne(mappedBy = "instructor")
    private Course course ;
    // Instructor → Department : @ManyToOne
    @ManyToOne
    @JoinColumn(name = "depId")
    private Department department ;



}
