package com.example.CourseSystem.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer depId;
    private String departmentName;
    private Boolean isActive;
    private LocalDate createdDate;
    private LocalDate updatedDate;

 //OPTIONAL: A department can have many instructors
  @OneToMany(mappedBy = "department")
  @JsonIgnore //don’t include this list in JSON (avoid infinite loops)
  private List<Instructor> instructors;

  // OPTIONAL: A department can have many courses
   @OneToMany(mappedBy = "department")
   @JsonIgnore //don’t include this list in JSON (avoid infinite loops)
  private List<Course> courses;

}
