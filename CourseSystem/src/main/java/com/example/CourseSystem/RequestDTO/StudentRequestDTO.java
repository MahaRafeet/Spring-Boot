package com.example.CourseSystem.RequestDTO;

import com.example.CourseSystem.Enume.Gender;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {
        private String firstName;
        private String lastName;
        private String email;
        private Date dateOfBirth;
        private Gender gender;
        private List<PhoneNumberRequestDTO> phoneNumbers;
        private AddressRequestDTO address; // optional

    public static void validateStudentRequest(StudentRequestDTO request) throws Exception {
        // Example basic checks
        if (Helper.isNull(request.getFirstName()) || Helper.isBlank(request.getFirstName())) {
            throw new Exception(Constans.BAD_REQUEST);
        }
        if (Helper.isNull(request.getLastName()) || Helper.isBlank(request.getLastName())) {
            throw new Exception(Constans.BAD_REQUEST);
        }

        if (Helper.isNull(request.getEmail()) || Helper.isBlank(request.getEmail())) {
            throw new Exception(Constans.BAD_REQUEST);
        }
        if (Helper.isNull(request.getGender())) {
            throw new Exception(Constans.BAD_REQUEST);
        }
        // Business rule: at least one phone number
        if (Helper.isNull(request.getPhoneNumbers()) || request.getPhoneNumbers().isEmpty()) {
            throw new Exception(Constans.STUDENT_MUST_HAVE_PHONE_NUMBER);
        }
        if (Helper.isNull(request.getDateOfBirth()) ){
    throw new Exception(Constans.BAD_REQUEST);
        }
        if(request.getDateOfBirth().after(new Date())) {
            throw new Exception(Constans.BIRTH_DAY_DATE_CAN_NOT_BE_IN_FUTURE);
        }
        //optional address -- validate only if available
        if (!Helper.isNull(request.getAddress())) {

        if (Helper.isNull(request.getAddress().getCity()) ||
                Helper.isBlank(request.getAddress().getCity())) {
            throw new Exception(Constans.CITY_IS_REQUIRED_IN_ADDRESS);
        }

        if (Helper.isNull(request.getAddress().getCountry()) ||
                Helper.isBlank(request.getAddress().getCountry())) {
            throw new Exception(Constans.COUNTRY_IS_REQUIRED_IN_ADDRESS);
        }


        }
    }


}




