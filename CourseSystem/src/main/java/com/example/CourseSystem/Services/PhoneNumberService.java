package com.example.CourseSystem.Services;

import com.example.CourseSystem.Entity.PhoneNumber;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.RequestDTO.PhoneNumberRequestDTO;
import com.example.CourseSystem.ResponseDTO.PhoneNumberResponseDTO;
import com.example.CourseSystem.repositories.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.CourseSystem.RequestDTO.PhoneNumberRequestDTO.validatePhoneNumberRequest;

@Service
public class PhoneNumberService {

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    // 1️⃣ CREATE
    public PhoneNumberResponseDTO createPhoneNumber(PhoneNumberRequestDTO requestDTO) throws Exception {
        // validate request
        validatePhoneNumberRequest(requestDTO);

        PhoneNumber phoneNumber = toEntity(requestDTO);
        PhoneNumber saved = phoneNumberRepository.save(phoneNumber);

        return toResponseDTO(saved);
    }

    // 2️⃣ GET BY ID (ACTIVE ONLY)
    public PhoneNumberResponseDTO getPhoneNumberById(Integer id) throws Exception {
        PhoneNumber phoneNumber = phoneNumberRepository.getActivePhoneNumberById(id);
        if (Helper.isNull(phoneNumber)) {
            throw new Exception(Constans.PHONE_NUMBER_NOT_FOUND);
        }
        return toResponseDTO(phoneNumber);
    }

    // 3️⃣ GET ALL ACTIVE
    public List<PhoneNumberResponseDTO> getAllPhoneNumbers() {
        List<PhoneNumber> list = phoneNumberRepository.getActivePhoneNumber();
        List<PhoneNumberResponseDTO> result = new ArrayList<>();

        if (Helper.isListNotEmpty(list)) {
            for (PhoneNumber number : list) {
                result.add(toResponseDTO(number));
            }
        }
        return result;
    }

    // UPDATE
    public PhoneNumberResponseDTO updatePhoneNumber(Integer id, PhoneNumberRequestDTO requestDTO) throws Exception {
        // validate request
        validatePhoneNumberRequest(requestDTO);

        PhoneNumber phoneNumber = phoneNumberRepository.getActivePhoneNumberById(id);
        if (Helper.isNull(phoneNumber)) {
            throw new Exception(Constans.PHONE_NUMBER_NOT_FOUND);
        }

        // update simple fields
        if (Helper.isNotNull(requestDTO.getNumber())) {
            phoneNumber.setNumber(requestDTO.getNumber());
        }
        if (Helper.isNotNull(requestDTO.getCountryCode())) {
            phoneNumber.setCountryCode(requestDTO.getCountryCode());
        }
        if (Helper.isNotNull(requestDTO.getIsLandLine())) {
            phoneNumber.setIsLandLine(requestDTO.getIsLandLine());
        }

        phoneNumber.setUpdatedDate(new Date());

        PhoneNumber updated = phoneNumberRepository.save(phoneNumber);
        return toResponseDTO(updated);
    }

    // SOFT DELETE
    public String deletePhoneNumber(Integer id) throws Exception {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(id).orElseThrow(
                () -> new Exception(Constans.STUDENT_NOT_FOUND));
        if (!phoneNumber.getIsActive()) {
            throw new Exception(Constans.PHONE_NUMBER_ALREADY_DELETED);
        }
            phoneNumber.setIsActive(false);
            phoneNumber.setUpdatedDate(new Date());
            phoneNumberRepository.save(phoneNumber);
        return "Student Deleted Successfully";
    }


    // REQUEST DTO → ENTITY (CREATE)
    public PhoneNumber toEntity(PhoneNumberRequestDTO requestDTO) {
        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setNumber(requestDTO.getNumber());
        phoneNumber.setCountryCode(requestDTO.getCountryCode());
        phoneNumber.setIsLandLine(requestDTO.getIsLandLine());

        phoneNumber.setIsActive(true);
        phoneNumber.setCreatedDate(new Date());
        phoneNumber.setUpdatedDate(new Date());

        return phoneNumber;
    }

    // 🔁 ENTITY → RESPONSE DTO
    public PhoneNumberResponseDTO toResponseDTO(PhoneNumber phoneNumber) {
        PhoneNumberResponseDTO responseDTO = new PhoneNumberResponseDTO();

        responseDTO.setId(phoneNumber.getId());
        responseDTO.setNumber(phoneNumber.getNumber());
        responseDTO.setCountryCode(phoneNumber.getCountryCode());

        return responseDTO;
    }
}
