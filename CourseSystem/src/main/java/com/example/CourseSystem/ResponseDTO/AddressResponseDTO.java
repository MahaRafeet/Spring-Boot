package com.example.CourseSystem.ResponseDTO;
import com.example.CourseSystem.Entity.Address;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.RequestDTO.AddressRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddressResponseDTO {
    private Integer id;
    private String street;
    private String city;
    private String country;


}
