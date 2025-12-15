package com.example.CourseSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Address")

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String  postalCode;
    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;

    //one student has one address
    @OneToOne
    @JoinColumn(name = "studentId")
    private Student student;

}
