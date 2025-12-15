package com.example.CourseSystem.RequestDTO;

import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PhoneNumberRequestDTO {
    private String number;
    private String countryCode;
    private Boolean isLandLine;

    // VALIDATION
    public static void validatePhoneNumberRequest(PhoneNumberRequestDTO request) throws Exception {

        // basic null check
        if (Helper.isNull(request)) {
            throw new Exception(Constans.BAD_REQUEST);
        }

        // number is required and not blank
        if (Helper.isNull(request.getNumber()) || Helper.isBlank(request.getNumber())) {
            throw new Exception(Constans.BAD_REQUEST);
        }

        // countryCode is required and not blank (e.g. "+968")
        if (Helper.isNull(request.getCountryCode()) || Helper.isBlank(request.getCountryCode())) {
            throw new Exception(Constans.BAD_REQUEST);
        }

        // isLandLine cannot be null (must be true/false)
        if (Helper.isNull(request.getIsLandLine())) {
            throw new Exception(Constans.BAD_REQUEST);
        }
    }
}









