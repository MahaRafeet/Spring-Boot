package com.example.CourseSystem.ResponseDTO;
import com.example.CourseSystem.Enume.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private Gender gender;
    private List<PhoneNumberResponseDTO> phoneNumberList;
    private AddressResponseDTO address;
    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;


}
