package com.example.CourseSystem.repositories;

import com.example.CourseSystem.Entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
    @Query("SELECT p FROM PhoneNumber p WHERE p.isActive=true AND p.id=:id")
    PhoneNumber getActivePhoneNumberById(Integer id);

    @Query("SELECT p FROM PhoneNumber p WHERE p.isActive=true")
    List<PhoneNumber> getActivePhoneNumber();
}
