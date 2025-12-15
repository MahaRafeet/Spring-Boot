package com.example.CourseSystem.Services;

import com.example.CourseSystem.Entity.Department;
import com.example.CourseSystem.Entity.Instructor;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.RequestDTO.InstructorRequestDTO;
import com.example.CourseSystem.ResponseDTO.InstructorResponseDTO;
import com.example.CourseSystem.repositories.DepartmentRepository;
import com.example.CourseSystem.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.CourseSystem.RequestDTO.InstructorRequestDTO.validateInstructorRequest;

@Service
public class InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    // 1️⃣ GET ALL ACTIVE INSTRUCTORS
    public List<InstructorResponseDTO> getAllInstructors() {
        // You need a method like this in your repository:
        // List<Instructor> findByIsActiveTrue();
        List<Instructor> instructorList = instructorRepository.findActiveInstructors();
        List<InstructorResponseDTO> responseList = new ArrayList<>();

        for (Instructor instructor : instructorList) {
            responseList.add(convertEntityDTOToResponse(instructor));
        }
        return responseList;
    }

    // 2️⃣ CREATE
    public InstructorResponseDTO createInstructor(InstructorRequestDTO request) throws Exception {
        validateInstructorRequest(request);

        Instructor instructor = convertRequestDtoToEntity(request);
        Instructor savedInstructor = instructorRepository.save(instructor);

        return convertEntityDTOToResponse(savedInstructor);
    }

    //  GET BY ID (ACTIVE ONLY)
    public InstructorResponseDTO getInstructorById(Integer id) throws Exception {

        Instructor instructor = instructorRepository.getActiveInstructorById(id);
        if (Helper.isNull(instructor)) {
            throw new Exception(Constans.INSTRUCTOR_NOT_FOUND);
        }
        return convertEntityDTOToResponse(instructor);
    }

    //  UPDATE
    public InstructorResponseDTO updateInstructor(Integer id, InstructorRequestDTO request) throws Exception {
        validateInstructorRequest(request);

        Instructor existingInstructor = instructorRepository.getActiveInstructorById(id);
        if (Helper.isNull(existingInstructor)) {
            throw new Exception(Constans.INSTRUCTOR_NOT_FOUND);
        }

        // Update simple fields
        existingInstructor.setInstructorName(request.getInstructorName());
        existingInstructor.setEmail(request.getEmail());

        // Update department (required by your validation)
        if (Helper.isNull(request.getDepartmentId())) {
            throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
        }

        Department department = departmentRepository.getActiveDepartmentById(request.getDepartmentId());
        if (Helper.isNull(department)) {
            throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
        }
        existingInstructor.setDepartment(department);

        Instructor updated = instructorRepository.save(existingInstructor);
        return convertEntityDTOToResponse(updated);
    }

    // ✅ SOFT DELETE (BEST PRACTICE)
    public void deleteInstructor(Integer id) throws Exception {

        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new Exception(Constans.INSTRUCTOR_NOT_FOUND));

        // already deleted
        if (Boolean.FALSE.equals(instructor.getIsActive())) {
            throw new Exception(Constans.INSTRUCTOR_ALREADY_DELETED);
        }
        instructor.setIsActive(false);
        instructorRepository.save(instructor);
    }

    //DTO → ENTITY (CREATE)
    public Instructor convertRequestDtoToEntity(InstructorRequestDTO requestDTO) throws Exception {
        Instructor instructor = new Instructor();
        instructor.setInstructorName(requestDTO.getInstructorName());
        instructor.setEmail(requestDTO.getEmail());
        instructor.setIsActive(true);

        if (Helper.isNotNull(requestDTO.getDepartmentId())) {
            Department department = departmentRepository.getActiveDepartmentById(requestDTO.getDepartmentId());
            if (Helper.isNull(department)) {
                throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
            }
            instructor.setDepartment(department);
        }

        return instructor;
    }

    //  ENTITY → RESPONSE DTO
    public InstructorResponseDTO convertEntityDTOToResponse(Instructor instructor) {
        InstructorResponseDTO instructorResponse = new InstructorResponseDTO();
        instructorResponse.setId(instructor.getId());
        instructorResponse.setInstructorName(instructor.getInstructorName());
        instructorResponse.setEmail(instructor.getEmail());
        instructorResponse.setIsActive(instructor.getIsActive());

        if (Helper.isNotNull(instructor.getDepartment())) {
            instructorResponse.setDepartmentId(instructor.getDepartment().getDepId());
            instructorResponse.setDepartmentName(instructor.getDepartment().getDepartmentName());
        } else {
            instructorResponse.setDepartmentId(null);
            instructorResponse.setDepartmentName(null);
        }

        return instructorResponse;
    }
}
