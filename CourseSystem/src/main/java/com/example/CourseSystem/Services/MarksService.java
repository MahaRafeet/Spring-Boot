package com.example.CourseSystem.Services;

import com.example.CourseSystem.Entity.Course;
import com.example.CourseSystem.Entity.Marks;
import com.example.CourseSystem.Exceptions.Constans;
import com.example.CourseSystem.HelperUtil.Helper;
import com.example.CourseSystem.RequestDTO.MarksRequestDTO;
import com.example.CourseSystem.ResponseDTO.MarksResponseDTO;
import com.example.CourseSystem.repositories.CourseRepository;
import com.example.CourseSystem.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.CourseSystem.RequestDTO.MarksRequestDTO.marksRequestValidation;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private CourseRepository courseRepository;

    // 1️⃣ CREATE
    public MarksResponseDTO createMarks(MarksRequestDTO requestDTO) throws Exception {
        // validate request
        marksRequestValidation(requestDTO);

        // check course existence
        Course course = courseRepository.getActiveCourseById(requestDTO.getCourseId());
        if (Helper.isNull(course)) {
            throw new Exception(Constans.COURSE_NOT_FOUND);
        }

        // map request → entity
        Marks marks = convertRequestToEntity(requestDTO, course);
        Marks saved = marksRepository.save(marks);

        return toResponseDTO(saved);
    }

    // 2️⃣ GET BY ID (ACTIVE ONLY)
    public MarksResponseDTO getMarksById(Integer id) throws Exception {
        Marks marks = marksRepository.getActiveMarksById(id);
        if (Helper.isNull(marks)) {
            throw new Exception(Constans.MARKS_NOT_FOUND);
        }
        return toResponseDTO(marks);
    }

    // 3️⃣ GET ALL ACTIVE
    public List<MarksResponseDTO> getAllMarks() {
        List<Marks> marksList = marksRepository.findAllActiveMarks();
        List<MarksResponseDTO> marksResponse = new ArrayList<>();

        if (Helper.isListNotEmpty(marksList)) {
            for (Marks mark : marksList) {
                marksResponse.add(toResponseDTO(mark));
            }
        }
        return marksResponse;
    }

    // 4️⃣ DELETE (SOFT DELETE)
    public String deleteMarks(Integer id) throws Exception {
        Marks marks = marksRepository.getActiveMarksById(id);
        if (Helper.isNull(marks)) {
            throw new Exception(Constans.MARKS_NOT_FOUND);
        }

        marks.setIsActive(false);
        marks.setUpdatedDate(new Date());
        marksRepository.save(marks);

        return "Marks deleted successfully";
    }

    // UPDATE
    public MarksResponseDTO update(Integer id, MarksRequestDTO requestDTO) throws Exception {
        // validate request
        marksRequestValidation(requestDTO);

        Marks existingMarks = marksRepository.getActiveMarksById(id);
        if (Helper.isNull(existingMarks)) {
            throw new Exception(Constans.MARKS_NOT_FOUND);
        }

        // check course existence
        Course course = courseRepository.getActiveCourseById(requestDTO.getCourseId());
        if (Helper.isNull(course)) {
            throw new Exception(Constans.COURSE_NOT_FOUND);
        }

        // update fields
        existingMarks.setStudentId(requestDTO.getStudentId());
        existingMarks.setScore(requestDTO.getScore());
        existingMarks.setCourse(course);
        existingMarks.setUpdatedDate(new Date());

        Marks saved = marksRepository.save(existingMarks);
        return toResponseDTO(saved);
    }

    // REQUEST DTO → ENTITY (for CREATE)
    private Marks convertRequestToEntity(MarksRequestDTO requestDTO, Course course) {
        Marks marks = new Marks();

        marks.setStudentId(requestDTO.getStudentId());
        marks.setScore(requestDTO.getScore());
        marks.setCourse(course);

        marks.setIsActive(true);
        marks.setCreatedDate(new Date());
        marks.setUpdatedDate(new Date());

        return marks;
    }

    // ENTITY → RESPONSE DTO
    private MarksResponseDTO toResponseDTO(Marks marks) {
        MarksResponseDTO marksResponseDTO = new MarksResponseDTO();

        // if MarksResponseDTO has id
        marksResponseDTO.setId(marks.getId());

        marksResponseDTO.setStudentId(marks.getStudentId());
        marksResponseDTO.setScore(marks.getScore());

        if (Helper.isNotNull(marks.getCourse())) {
            marksResponseDTO.setCourseId(marks.getCourse().getId());
        }

        return marksResponseDTO;
    }
}
