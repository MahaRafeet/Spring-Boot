package com.example.CourseSystem.repositories;

import com.example.CourseSystem.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

    @Query("SELECT a FROM Address a WHERE a.isActive=true AND a.Id=:Id")
    Address getActiveAddressById(@Param("id")Integer Id);
    @Query("SELECT a FROM Address a WHERE a.isActive=true")
    List<Address> getAllActiveAddress();
}
