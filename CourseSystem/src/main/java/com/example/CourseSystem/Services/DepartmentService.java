package com.example.CourseSystem.Services;

import com.example.CourseSystem.Entity.Course;
import com.example.CourseSystem.Entity.Department;
import com.example.CourseSystem.Entity.Instructor;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.RequestDTO.DepartmentRequestDTO;
import com.example.CourseSystem.ResponseDTO.DepartmentResponseDTO;
import com.example.CourseSystem.repositories.CourseRepository;
import com.example.CourseSystem.repositories.DepartmentRepository;
import com.example.CourseSystem.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    // You actually do not need these two repositories in this service now,
    // because you fetch courses/instructors from department.getCourses()/getInstructors().
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorRepository instructorRepository;

    // 1️⃣ GET ALL ACTIVE DEPARTMENTS
    public List<DepartmentResponseDTO> getAllDepartments() {
        List<Department> departmentList = departmentRepository.findByIsActiveTrue();
        List<DepartmentResponseDTO> departmentResponseDTOList = new ArrayList<>();

        for (Department department : departmentList) {
            departmentResponseDTOList.add(convertEntityToDTO(department));
        }
        return departmentResponseDTOList;
    }

    // 2️⃣ CREATE
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO request) throws Exception {
        validateDepartment(request);

        Department department = convertDTOToEntity(request);
        Department savedDepartment = departmentRepository.save(department);

        return convertEntityToDTO(savedDepartment);
    }

    // 3️⃣ UPDATE
    public DepartmentResponseDTO updateDepartment(Integer id, DepartmentRequestDTO request) throws Exception {
        validateDepartment(request);

        Department existingDepartment = departmentRepository.getActiveDepartmentById(id);
        if (Helper.isNull(existingDepartment)) {
            throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
        }

        existingDepartment.setDepartmentName(request.getDepartmentName());
        existingDepartment.setUpdatedDate(LocalDate.now());

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return convertEntityToDTO(updatedDepartment);
    }

    // 4️⃣ GET BY ID (ACTIVE ONLY)
    public DepartmentResponseDTO getById(Integer id) throws Exception {
        Department department = departmentRepository.getActiveDepartmentById(id);
        if (Helper.isNull(department)) {
            throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
        }
        return convertEntityToDTO(department);
    }

    // SOFT DELETE
    public void deleteDepartment(Integer id) throws Exception {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new Exception(Constans.DEPARTMENT_NOT_FOUND));

        // already deleted
        if (!department.getIsActive()) {
            throw new Exception(Constans.DEPARTMENT_ALREADY_DELETED);
        }

        department.setIsActive(false);
        department.setUpdatedDate(LocalDate.now());

        departmentRepository.save(department);
    }


    // ENTITY → RESPONSE DTO
    public DepartmentResponseDTO convertEntityToDTO(Department department) {

        // Instructor names
        List<String> instructorNames = new ArrayList<>();
        if (Helper.isNotNull(department.getInstructors())) {
            for (Instructor instructor : department.getInstructors()) {
                instructorNames.add(instructor.getInstructorName());
            }
        }

        // Course names
        List<String> courseNames = new ArrayList<>();
        if (Helper.isNotNull(department.getCourses())) {
            for (Course course : department.getCourses()) {
                courseNames.add(course.getCourseName());
            }
        }

        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setDepartmentName(department.getDepartmentName());
        dto.setDepId(department.getDepId());
        dto.setIsActive(department.getIsActive());
        dto.setCreatedDate(department.getCreatedDate());
        dto.setUpdatedDate(department.getUpdatedDate());
        dto.setInstructorNames(instructorNames);
        dto.setCourseNames(courseNames);

        return dto;
    }

    // DTO → ENTITY (for CREATE)
    public Department convertDTOToEntity(DepartmentRequestDTO requestDTO) {
        Department department = new Department();
        department.setDepartmentName(requestDTO.getDepartmentName());
        department.setDepId(requestDTO.getDepId());
        department.setIsActive(true);
        department.setCreatedDate(LocalDate.now());
        department.setUpdatedDate(LocalDate.now());
        return department;
    }

    // VALIDATION (similar to validateCourseRequest style)
    public void validateDepartment(DepartmentRequestDTO departmentRequestDTO) throws Exception {
        if (Helper.isNull(departmentRequestDTO.getDepartmentName())
                || departmentRequestDTO.getDepartmentName().isEmpty()) {
            throw new Exception(Constans.DEPARTMENT_NAME_INVALID);
        }
    }
}
