package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {
private Map<Integer, String> courses = new HashMap<>() ;
    private int idCounter = 1;
    @PostMapping("create")
    public String createCourse(@RequestParam String name) {
        courses.put(idCounter, name);
        return "Course created with ID: " + idCounter++;
    }
    @GetMapping("getAll")
public Map<Integer, String> getAllCourses() {
        return courses;
    }

    @GetMapping("getById/{id}")
    public String getCourseById(@PathVariable Integer id) {
        return courses.getOrDefault(id, "Course not found");
    }

    @PutMapping("update/{id}")
    public String updateCourse(@PathVariable Integer id, @RequestParam String name) {
        if (courses.containsKey(id)) {
            courses.put(id, name);
            return "Course updated";
        } else {
            return "Course not found";
        }
    }
    @DeleteMapping("delete/{id}")
    public String deleteCourse(@PathVariable int id) {
        if (courses.containsKey(id)) {
            courses.remove(id);
            return "Course deleted";
        } else {
            return "Course not found";
        }
    }


}
