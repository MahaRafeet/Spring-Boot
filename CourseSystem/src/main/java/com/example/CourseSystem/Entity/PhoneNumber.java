package com.example.CourseSystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "PhoneNumbers")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
   private String  number;
   private String countryCode;
   private Boolean isLandLine;
   private Boolean isActive;
   private Date createdDate;
   private Date updatedDate;

   @ManyToOne
   @JoinColumn(name="studentId")
   @JsonIgnore
   private Student student;


}
