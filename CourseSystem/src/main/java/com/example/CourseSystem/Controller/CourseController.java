package com.example.CourseSystem.Controller;

import com.example.CourseSystem.Entity.Course;
import com.example.CourseSystem.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping("create")
    public Course createCourse(@RequestBody Course requestObj) {
        Course course =courseService.saveCourse(requestObj);
        return course;

    }

    // 2️⃣ GET ALL ACTIVE COURSES
    @GetMapping("getAll")
    public List<Course> getAllCourses() {
        List<Course> courseList = courseService.getAllCourses();
        return courseList;
    }

    // 3️⃣ GET COURSE BY ID
    @GetMapping("getById")
    public Course getCourseById(@RequestParam int id) throws Exception {
        return courseService.getCourseById(id);
    }

    // 4️⃣ UPDATE COURSE
    @PutMapping("update")
    public Course updateCourse(@RequestBody Course updateObjFromUser) throws Exception {
        return courseService.updateCourse(updateObjFromUser);
    }

    // 5️⃣ DELETE COURSE (SOFT DELETE)
    @DeleteMapping("delete/{id}")
    public String deleteCourse(@PathVariable int id) throws Exception {
        courseService.courseDelete(id);
        return "Course deleted successfully";
    }

}
