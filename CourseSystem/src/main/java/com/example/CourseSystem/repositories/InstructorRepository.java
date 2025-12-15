package com.example.CourseSystem.repositories;

import com.example.CourseSystem.Entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    @Query("SELECT i FROM Instructor i WHERE i.isActive=true ")
    List<Instructor> findActiveInstructors();
    @Query("SELECT i FROM Instructor i WHERE i.isActive=true AND i.id=:id")
    Instructor getActiveInstructorById(Integer id );
}
