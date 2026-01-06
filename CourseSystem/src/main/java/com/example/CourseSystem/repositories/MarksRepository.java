package com.example.CourseSystem.repositories;

import com.example.CourseSystem.Entity.Department;
import com.example.CourseSystem.Entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Integer> {

    @Query("SELECT m FROM Marks m WHERE m.isActive=true AND m.id=:id")
    Marks getActiveMarksById(Integer id);

    @Query("SELECT m FROM Marks m WHERE m.isActive=true")
    List<Marks> findAllActiveMarks();


}
