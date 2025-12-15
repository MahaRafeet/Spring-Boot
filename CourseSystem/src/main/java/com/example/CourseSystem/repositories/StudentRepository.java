package com.example.CourseSystem.repositories;

import com.example.CourseSystem.Entity.Student;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{
    @Query("SELECT s FROM Student s WHERE s.isActive=true AND s.id=:id")
     Student getActiveStudentById(Integer id);
    @Query("SELECT s FROM Student s WHERE s.isActive=true")
    List<Student> getAllActiveStudent();


}