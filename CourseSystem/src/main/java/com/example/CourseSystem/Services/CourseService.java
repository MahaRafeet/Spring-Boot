package com.example.CourseSystem.Services;

import com.example.CourseSystem.Entity.Course;
import com.example.CourseSystem.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course){
        course.setIsActive(true);
        course.setCreatedDate(new Date());
       return courseRepository.save(course);
    }

    public Course updateCourse(Course course) throws Exception {
        Course existingCourse=courseRepository.findById(course.getId()).get();
        if(existingCourse!=null && existingCourse.getIsActive()){
            course.setUpdatedDate(new Date());
            return courseRepository.save(course);
        }
        else{
            throw new Exception ("BAD REQUEST");
        }
    }

    public void courseDelete(int id) throws Exception {
        Course existingCourse=courseRepository.findById(id).get();
        if(existingCourse!=null && existingCourse.getIsActive()){
            existingCourse.setIsActive(false);
            existingCourse.setUpdatedDate(new Date());
            courseRepository.save(existingCourse);
        }
      else{
          throw new Exception ("BAD REQUEST");
        }

    }

    public Course getCourseById(int id) throws Exception {
        Course existingCourse=courseRepository.findById(id).get();
        if(existingCourse!=null && existingCourse.getIsActive()){
            return existingCourse;
        }
        else{
            throw new Exception ("BAD REQUEST");
        }
    }





}
