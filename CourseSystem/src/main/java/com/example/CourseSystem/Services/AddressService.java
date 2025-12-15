package com.example.CourseSystem.Services;

import com.example.CourseSystem.Entity.Address;
import com.example.CourseSystem.Entity.Student;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.RequestDTO.AddressRequestDTO;
import com.example.CourseSystem.ResponseDTO.AddressResponseDTO;
import com.example.CourseSystem.repositories.AddressRepository;
import com.example.CourseSystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    StudentRepository studentRepository;

    // 1️⃣ CREATE ADDRESS (for a student who does NOT have address yet)
    public AddressResponseDTO createAddress(Integer studentId, AddressRequestDTO requestDTO) throws Exception {

        // validate request using your method
        requestDTO.validateAddressRequest(requestDTO);

        // check student
        Student student = studentRepository.getActiveStudentById(studentId);
        if (Helper.isNull(student)) {
            throw new Exception(Constans.STUDENT_NOT_FOUND);
        }

        // business rule: one address per student
        if (Helper.isNotNull(student.getAddress())) {
            throw new Exception("Student already has an address, please use update.");
        }

        // map DTO → Entity
        Address address = convertDTOToEntity(requestDTO);
        address.setStudent(student);

        Address saved = addressRepository.save(address);

        // link back to student
        student.setAddress(saved);
        studentRepository.save(student);

        return convertEntityToDTO(saved);
    }

    // UPDATE ADDRESS (for a student who ALREADY has address)
    public AddressResponseDTO updateAddress(Integer studentId, AddressRequestDTO requestDTO) throws Exception {

        // validate request
        requestDTO.validateAddressRequest(requestDTO);

        // check student
        Student student = studentRepository.getActiveStudentById(studentId);
        if (Helper.isNull(student)) {
            throw new Exception(Constans.STUDENT_NOT_FOUND);
        }

        Address existingAddress = student.getAddress();
        if (Helper.isNull(existingAddress)) {
            throw new Exception("Student does not have an address to update.");
        }

        // update existing entity fields (same style as CourseService.updateCourse)
        existingAddress.setHouseNumber(requestDTO.getHouseNumber());
        existingAddress.setStreet(requestDTO.getStreet());
        existingAddress.setCity(requestDTO.getCity());
        existingAddress.setCountry(requestDTO.getCountry());
        existingAddress.setPostalCode(requestDTO.getPostalCode());
        existingAddress.setUpdatedDate(new Date());

        Address updated = addressRepository.save(existingAddress);

        return convertEntityToDTO(updated);
    }

    // 3️⃣ GET BY ID
    public AddressResponseDTO getAddressById(Integer id) throws Exception {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new Exception(Constans.BAD_REQUEST));

        if (Boolean.FALSE.equals(address.getIsActive())) {
            throw new Exception(Constans.BAD_REQUEST);
        }

        return convertEntityToDTO(address);
    }
    public void deleteAddress(Integer id) throws Exception {

        Address address = addressRepository.getActiveAddressById(id);
        if(Helper.isNull(address)){
           throw new Exception(Constans.ADDRESS_NOT_FOUND);
        }

        address.setIsActive(false);
        address.setUpdatedDate(new Date());

        addressRepository.save(address);
    }
    // ----------------- MAPPING HELPERS (your style) -----------------

    // DTO → ENTITY (CREATE)
    public Address convertDTOToEntity(AddressRequestDTO dto) {
        Address address = new Address();

        address.setHouseNumber(dto.getHouseNumber());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setPostalCode(dto.getPostalCode());

        address.setIsActive(true);
        address.setCreatedDate(new Date());
        address.setUpdatedDate(new Date());

        return address;
    }

    // ENTITY → DTO (RESPONSE)
    public AddressResponseDTO convertEntityToDTO(Address address) {
        if(Helper.isNull(address)){
            return null;
        }
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        return dto;
    }
}
