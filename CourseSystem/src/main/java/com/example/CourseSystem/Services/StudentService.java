package com.example.CourseSystem.Services;

import com.example.CourseSystem.Entity.Address;
import com.example.CourseSystem.Entity.PhoneNumber;
import com.example.CourseSystem.Entity.Student;
import com.example.CourseSystem.Enume.Gender;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.RequestDTO.PhoneNumberRequestDTO;
import com.example.CourseSystem.RequestDTO.StudentRequestDTO;
import com.example.CourseSystem.ResponseDTO.AddressResponseDTO;
import com.example.CourseSystem.ResponseDTO.PhoneNumberResponseDTO;
import com.example.CourseSystem.ResponseDTO.StudentResponseDTO;
import com.example.CourseSystem.repositories.AddressRepository;
import com.example.CourseSystem.repositories.PhoneNumberRepository;
import com.example.CourseSystem.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.CourseSystem.RequestDTO.StudentRequestDTO.validateStudentRequest;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PhoneNumberRepository phoneNumberRepository;
    @Autowired
    PhoneNumberService phoneNumberService;
    @Autowired
    AddressService addressService;

    // CREATE
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) throws Exception {
        validateStudentRequest(studentRequestDTO);

        // 1) save student first
        Student student = toEntity(studentRequestDTO);
        Student savedStudent = studentRepository.save(student);

        // 2) save address only if exists
        if (Helper.isNotNull(studentRequestDTO.getAddress())) {
            Address address = addressService.convertDTOToEntity(studentRequestDTO.getAddress());
            address.setStudent(savedStudent);
            Address savedAddress = addressRepository.save(address);
            savedStudent.setAddress(savedAddress);
        }

        // 3) save phone numbers (required)
        List<PhoneNumber> phones = new ArrayList<>();

        for (PhoneNumberRequestDTO pDto : studentRequestDTO.getPhoneNumbers()) {

            // convert request DTO → entity
            PhoneNumber phone = phoneNumberService.toEntity(pDto);

            // link phone number to the student
            phone.setStudent(savedStudent);

            // add to list
            phones.add(phone);
        }

// save all phone numbers
        phoneNumberRepository.saveAll(phones);

// attach phones to student (for response)
        savedStudent.setPhoneNumberList(phones);

        phoneNumberRepository.saveAll(phones);
        savedStudent.setPhoneNumberList(phones);

        return toResponseDTO(savedStudent);
    }


    //  GET BY ID
    public StudentResponseDTO getStudentById(Integer id) throws Exception {
        Student student = studentRepository.getActiveStudentById(id);
        if (Helper.isNull(student)) {
            throw new Exception(Constans.STUDENT_NOT_FOUND);
        }
        return toResponseDTO(student);
    }

    //GET ALL ACTIVE
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> studentList = studentRepository.getAllActiveStudent();
        List<StudentResponseDTO> studentResponseDTOS = new ArrayList<>();

        if (Helper.isListNotEmpty(studentList)) {
            for (Student student : studentList) {
                studentResponseDTOS.add(toResponseDTO(student));
            }
        }
        return studentResponseDTOS;
    }

    public StudentResponseDTO updateStudent(Integer id, StudentRequestDTO requestDTO) throws Exception {

        StudentRequestDTO.validateStudentRequest(requestDTO); // or separate update validation

        Student student = studentRepository.getActiveStudentById(id);
        if (Helper.isNull(student)) {
            throw new Exception(Constans.STUDENT_NOT_FOUND);
        }

        // update basic fields
        student.setFirstName(requestDTO.getFirstName());
        student.setLastName(requestDTO.getLastName());
        student.setEmail(requestDTO.getEmail());
        student.setDateOfBirth(requestDTO.getDateOfBirth());

        if (Helper.isNotNull(requestDTO.getGender())) {
            student.setGender(requestDTO.getGender());
        }

        student.setUpdatedDate(new Date());
        Student savedStudent = studentRepository.save(student);
        StudentResponseDTO studentResponse = toResponseDTO(savedStudent);

        AddressResponseDTO updatedAddressResponse = new AddressResponseDTO();

        // Address
        if (Helper.isNotNull(requestDTO.getAddress())) {

            updatedAddressResponse = addressService.updateAddress(savedStudent.getId(), requestDTO.getAddress());

           /* Address current = savedStudent.getAddress(); // may be null

            // if no address before, create new
            if (current == null) {
                current = new Address();
                current.setCreatedDate(new Date());
                current.setIsActive(true);
            } else {
                current.setUpdatedDate(new Date());
            }

            // update address fields
            current.setHouseNumber(requestDTO.getAddress().getHouseNumber());
            current.setStreet(requestDTO.getAddress().getStreet());
            current.setCity(requestDTO.getAddress().getCity());
            current.setCountry(requestDTO.getAddress().getCountry());
            current.setPostalCode(requestDTO.getAddress().getPostalCode());

            // VERY IMPORTANT: link it to the student
            current.setStudent(savedStudent);

            // save the address
            Address savedAddress = addressRepository.save(current);

            // optional, so response includes it
            savedStudent.setAddress(savedAddress);*/
            studentResponse.setAddress(updatedAddressResponse);
            return studentResponse;
        } else {
            return null;
        }


    }


    //  DELETE
    public String deleteStudent(Integer id) throws Exception {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new Exception(Constans.STUDENT_NOT_FOUND));

        student.setActive(false);
        student.setUpdatedDate(new Date());
        studentRepository.save(student);

        return "Student Deleted Successfully";
    }
    // ----------------- Mapping helpers -----------------

    // DTO → Entity (CREATE)
    private Student toEntity(StudentRequestDTO dto) {
        Student s = new Student();
        s.setFirstName(dto.getFirstName());
        s.setLastName(dto.getLastName());
        s.setEmail(dto.getEmail());
        s.setDateOfBirth(dto.getDateOfBirth());

        if (Helper.isNotNull(dto.getGender())) {
            s.setGender(Gender.valueOf(dto.getGender().toString()));
        }

        s.setActive(true);
        s.setCreatedDate(new Date());
        s.setUpdatedDate(new Date());
        return s;
    }

    // Entity → ResponseDTO
    private StudentResponseDTO toResponseDTO(Student s) {
        StudentResponseDTO dto = new StudentResponseDTO();

        dto.setId(s.getId());
        dto.setFirstName(s.getFirstName());
        dto.setLastName(s.getLastName());
        dto.setEmail(s.getEmail());
        dto.setDateOfBirth(s.getDateOfBirth());
        dto.setGender(s.getGender());

        // Phone numbers
        List<PhoneNumberResponseDTO> phoneDTOList = new ArrayList<>();
        if (Helper.isListNotEmpty(s.getPhoneNumberList())) {
            for (PhoneNumber pn : s.getPhoneNumberList()) {
                phoneDTOList.add(phoneNumberService.toResponseDTO(pn));
            }
        }
        dto.setPhoneNumberList(phoneDTOList);

        // Address
        dto.setAddress(addressService.convertEntityToDTO(s.getAddress()));

        return dto;
    }


}
