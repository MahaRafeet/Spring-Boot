package com.example.CourseSystem.Controller;

import com.example.CourseSystem.RequestDTO.DepartmentRequestDTO;
import com.example.CourseSystem.ResponseDTO.DepartmentResponseDTO;
import com.example.CourseSystem.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @PostMapping("create")
    public DepartmentResponseDTO createDepartment(@RequestBody DepartmentRequestDTO requestObj) throws Exception {
        return departmentService.createDepartment(requestObj);
    }

    @PutMapping("update/{id}")
    public DepartmentResponseDTO updateDepartment(@PathVariable Integer id ,@RequestBody DepartmentRequestDTO updatedDepartment) throws Exception {
        return departmentService.updateDepartment(id,updatedDepartment);
    }

    @GetMapping("getById")
    public DepartmentResponseDTO getById(@RequestParam Integer id)throws Exception{
        return departmentService.getById(id);
    }
    @GetMapping("getAll")
    public List<DepartmentResponseDTO> getALL(){
        return departmentService.getAllDepartments();
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable Integer id) throws Exception {
         departmentService.deleteDepartment(id);
         return "Department Deleted successfully";

    }






}
