package com.example.cars.repository;

import com.example.cars.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository <Log, Long>{
    // in progress, will be updated after successful connection of logs to the database
}
