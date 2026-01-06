package com.example.CourseSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Marks")
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private Integer studentId;
    private Double score;
    // Marks → Course : @ManyToOne
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;
}
