package com.example.CourseSystem.Services;

import com.example.CourseSystem.RequestDTO.CourseRequestDTO;
import com.example.CourseSystem.ResponseDTO.CourseResponseDTO;
import com.example.CourseSystem.Entity.Course;
import com.example.CourseSystem.Entity.Department;
import com.example.CourseSystem.Entity.Instructor;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.repositories.CourseRepository;
import com.example.CourseSystem.repositories.DepartmentRepository;
import com.example.CourseSystem.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.CourseSystem.RequestDTO.CourseRequestDTO.validateCourseRequest;
import static com.example.CourseSystem.ResponseDTO.CourseResponseDTO.convertEntityToDTO;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    DepartmentRepository departmentRepository;



    public List<CourseResponseDTO> getAllCourses() {
      List<Course> courseList=courseRepository.findCoursesByIsActiveTrue();
      List<CourseResponseDTO> courseResponseDTOList=new ArrayList<>();
     for(Course course :courseList){
            courseResponseDTOList.add(convertEntityToDTO(course));
     }
     return courseResponseDTOList;

    }

    public CourseResponseDTO createCourse(CourseRequestDTO request) throws Exception {
        validateCourseRequest(request);
        Course course = convertDTOToEntity(request);
        Course savedCourse = courseRepository.save(course);
        return convertEntityToDTO(savedCourse);
    }


        public CourseResponseDTO updateCourse(Integer id,CourseRequestDTO requestDTO) throws Exception {
            validateCourseRequest(requestDTO);
            //  Load existing course or throw if not found
            Course existingCourse = courseRepository.getActiveCourseById(id);
                   if(Helper.isNull(existingCourse)) {
                       throw new Exception(Constans.COURSE_NOT_FOUND);
                   }

            // Update simple fields
            existingCourse.setUpdatedDate(new Date());
            existingCourse.setCourseName(requestDTO.getCourseName());
            existingCourse.setHours(requestDTO.getHours());

            // Update department if provided
            if (Helper.isNull(requestDTO.getDepartmentId())) {
                throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
            }
            Department department = departmentRepository.getActiveDepartmentById(requestDTO.getDepartmentId());
            if(Helper.isNull(department)){
                throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
            }
                existingCourse.setDepartment(department);

            //  Update instructor if provided
            Instructor instructor = instructorRepository.getActiveInstructorById(requestDTO.getInstructorId());
            if (Helper.isNull(instructor)) {
                throw new Exception(Constans.INSTRUCTOR_NOT_FOUND);
            }
                existingCourse.setInstructor(instructor);

            //  Save and return DTO
            Course updatedCourse = courseRepository.save(existingCourse);
            return convertEntityToDTO(updatedCourse); // or convertEntityToDto if you rename it
        }
    public void courseDelete(int id) throws Exception {

        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new Exception(Constans.COURSE_NOT_FOUND));

        // check if already deleted
        if (!existingCourse.getIsActive()) {
            throw new Exception(Constans.Course_ALREADY_DELETED);
        }

        existingCourse.setIsActive(false);
        existingCourse.setUpdatedDate(new Date());

        courseRepository.save(existingCourse);
    }
        public CourseResponseDTO getCourseById ( int id) throws Exception {
            Course existingCourse = courseRepository.getActiveCourseById(id);
            if(Helper.isNull(existingCourse)) {
                throw new Exception(Constans.COURSE_NOT_FOUND);
            }
            else{
                return convertEntityToDTO(existingCourse);
            }

        }

    public Course convertDTOToEntity(CourseRequestDTO courseRequestDTO) throws Exception {
        validateCourseRequest(courseRequestDTO);

        Course course = new Course();
        course.setCourseName(courseRequestDTO.getCourseName());
        course.setHours(courseRequestDTO.getHours());

        course.setIsActive(true);
        course.setCreatedDate(new Date());
        course.setUpdatedDate(new Date());

        if (Helper.isNotNull(courseRequestDTO.getDepartmentId())) {
            Department department = departmentRepository.getActiveDepartmentById(courseRequestDTO.getDepartmentId());
            if (Helper.isNull(department)) {
                throw new Exception(Constans.DEPARTMENT_NOT_FOUND);
            }
            course.setDepartment(department);
        }

        if (Helper.isNotNull(courseRequestDTO.getInstructorId())) {
            Instructor instructor = instructorRepository.getActiveInstructorById(courseRequestDTO.getInstructorId());
            if (Helper.isNull(instructor)) {
                throw new Exception(Constans.INSTRUCTOR_NOT_FOUND);
            }
            course.setInstructor(instructor);
        }

        return course;
    }


}


