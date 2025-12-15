package com.example.CourseSystem.Controller;

import com.example.CourseSystem.RequestDTO.StudentRequestDTO;
import com.example.CourseSystem.ResponseDTO.StudentResponseDTO;
import com.example.CourseSystem.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // CREATE
    @PostMapping("/create")
    public StudentResponseDTO create(@RequestBody StudentRequestDTO dto) throws Exception {
        return studentService.createStudent(dto);
    }

    // GET ALL
    @GetMapping("/getAll")
    public List<StudentResponseDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    // GET BY ID
    @GetMapping("/getById")
    public StudentResponseDTO getStudentById(@RequestParam Integer id) throws Exception {
        return studentService.getStudentById(id);
    }

    // UPDATE
    @PutMapping("/update")
    public StudentResponseDTO update(@RequestParam Integer id,
                                     @RequestBody StudentRequestDTO dto) throws Exception {
        return studentService.updateStudent(id, dto);
    }

    // DELETE
    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) throws Exception {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }
}