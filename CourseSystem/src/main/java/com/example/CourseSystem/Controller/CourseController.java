package com.example.CourseSystem.Controller;

import com.example.CourseSystem.Entity.Course;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("course")
public class CourseController {

    private List<Course> courseList = new ArrayList<>();
    private int idCounter = 1;
    // 1️⃣ CREATE COURSE
    @PostMapping("create")
    public String createCourse(@RequestBody Course requestObj) {

        requestObj.setId(idCounter);
        requestObj.setCreatedDate(new Date());
        requestObj.setIsActive(true);

        courseList.add(requestObj);

        return "Course created with ID: " + idCounter++;
    }

    // 2️⃣ GET ALL ACTIVE COURSES
    @GetMapping("getAll")
    public List<Course> getAllCourses() {
        List<Course> responseList = new ArrayList<>();

        for (Course c : courseList) {
            if (c.getIsActive()) {
                responseList.add(c);
            }
        }
        return responseList;
    }

    // 3️⃣ GET COURSE BY ID
    @GetMapping("getById")
    public Course getCourseById(@RequestParam int id) {

        for (Course c : courseList) {
            if (c.getId() == id && c.getIsActive()) {
                return c;
            }
        }

        // return empty object if not found
        return Course.builder().build();
    }

    // 4️⃣ UPDATE COURSE
    @PutMapping("update")
    public String updateCourse(@RequestBody Course updateObjFromUser) {

        if (updateObjFromUser != null && updateObjFromUser.getId() != null) {

            Course existingCourse = findCourseById(updateObjFromUser.getId());

            courseList.remove(existingCourse);

            existingCourse.setName(updateObjFromUser.getName());
            existingCourse.setInstructor(updateObjFromUser.getInstructor());
            existingCourse.setHours(updateObjFromUser.getHours());
            existingCourse.setUpdatedDate(new Date());

            courseList.add(existingCourse);

            return "Course updated successfully";
        }

        return "Course not found";
    }

    // 5️⃣ DELETE COURSE (SOFT DELETE)
    @DeleteMapping("delete/{id}")
    public String deleteCourse(@PathVariable int id) {

        Course existingCourse = findCourseById(id);

        if (existingCourse.getId() > 0) {
            courseList.remove(existingCourse);

            existingCourse.setIsActive(false);
            existingCourse.setUpdatedDate(new Date());

            courseList.add(existingCourse);

            return "Course deleted successfully";
        }

        return "Invalid id";
    }

    // Helper Method
    public Course findCourseById(int id) {
        for (Course c : courseList) {
            if (c.getId() == id) {
                return c;
            }
        }
        return Course.builder().id(-1).build();
    }
}
