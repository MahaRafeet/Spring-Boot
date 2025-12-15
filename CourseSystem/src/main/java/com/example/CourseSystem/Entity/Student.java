package com.example.CourseSystem.Entity;

import com.example.CourseSystem.Enume.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean isActive;
    private Date createdDate;
    private Date updatedDate;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<PhoneNumber> phoneNumberList;
    //student -address
    @OneToOne
    private Address address;

}
