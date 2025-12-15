package com.example.CourseSystem.Controller;

import com.example.CourseSystem.RequestDTO.PhoneNumberRequestDTO;
import com.example.CourseSystem.ResponseDTO.PhoneNumberResponseDTO;
import com.example.CourseSystem.Services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("phoneNumbers")
public class PhoneNumberController {

    @Autowired
    PhoneNumberService phoneNumberService;

    // UPDATE PHONE NUMBER
    @PutMapping
    public PhoneNumberResponseDTO updatePhoneNumber(@PathVariable Integer id,
                                                    @RequestBody PhoneNumberRequestDTO updatedObj) throws Exception {
        return phoneNumberService.updatePhoneNumber(id, updatedObj);
    }

    // DELETE PHONE NUMBER
    @DeleteMapping
    public String deletePhoneNumber(@PathVariable Integer id) throws Exception {
        phoneNumberService.deletePhoneNumber(id);
        return "Phone number deleted successfully";
    }
}
