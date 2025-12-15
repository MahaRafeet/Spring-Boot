package com.example.CourseSystem.Controller;

import com.example.CourseSystem.RequestDTO.CourseRequestDTO;
import com.example.CourseSystem.ResponseDTO.CourseResponseDTO;
import com.example.CourseSystem.Services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // CREATE
    @PostMapping("/create")
    public CourseResponseDTO createCourse(@RequestBody CourseRequestDTO requestObj) throws Exception {
        return courseService.createCourse(requestObj);
    }

    // GET ALL
    @GetMapping("/getAll")
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    // GET BY ID
    @GetMapping("/getById")
    public CourseResponseDTO getCourseById(@RequestParam int id) throws Exception {
        return courseService.getCourseById(id);
    }

    // UPDATE
    @PutMapping("/update")
    public CourseResponseDTO updateCourse(
            @RequestParam int id,
            @RequestBody CourseRequestDTO updatedCourse) throws Exception {
        return courseService.updateCourse(id, updatedCourse);
    }

    // DELETE (SOFT DELETE)
    @DeleteMapping("/delete")
    public String deleteCourse(@RequestParam int id) throws Exception {
        courseService.courseDelete(id);
        return "Course deleted successfully";
    }
}
