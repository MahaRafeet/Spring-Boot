package com.example.CourseSystem.repositories;

import com.example.CourseSystem.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("SELECT d FROM Department d WHERE d.isActive=true AND d.depId=:depId")
     Department getActiveDepartmentById(Integer depId);

    @Query("SELECT d FROM Department d WHERE d.isActive=true")
    List<Department> findByIsActiveTrue();

}
