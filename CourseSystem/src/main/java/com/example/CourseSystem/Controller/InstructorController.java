package com.example.CourseSystem.Controller;

import com.example.CourseSystem.RequestDTO.InstructorRequestDTO;
import com.example.CourseSystem.ResponseDTO.InstructorResponseDTO;
import com.example.CourseSystem.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("create")
    public InstructorResponseDTO create(@RequestBody InstructorRequestDTO requestObj) throws Exception {
        return instructorService.createInstructor(requestObj);
    }

    @PutMapping("update/{id}")
    public InstructorResponseDTO update(@PathVariable Integer id,
                                        @RequestBody InstructorRequestDTO updatedObj) throws Exception {
        return instructorService.updateInstructor(id, updatedObj);
    }

    @GetMapping("getById")
    public InstructorResponseDTO getById(@RequestParam Integer id) throws Exception {
        return instructorService.getInstructorById(id);
    }

    @GetMapping("getAll")
    public List<InstructorResponseDTO> getAll() {
        return instructorService.getAllInstructors();
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable Integer id) throws Exception {
        instructorService.deleteInstructor(id);
        return "Instructor deleted successfully";
    }
}
