package com.example.cars.repository;

import com.example.cars.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT " +
            "COUNT(CASE WHEN c.isDrivable = true THEN 1 END) AS drivableCars, " +
            "COUNT(CASE WHEN c.isDrivable = false THEN 1 END) AS nonDrivableCars " +
            "FROM Car c WHERE c.model.name = :modelName")
    Map<String, Long> getCarStatsByModel(@Param("modelName") String modelName);

    @Query("SELECT c.model.name AS modelName, " +
            "COUNT(CASE WHEN c.isDrivable = true THEN 1 END) AS drivableCars, " +
            "COUNT(CASE WHEN c.isDrivable = false THEN 1 END) AS nonDrivableCars " +
            "FROM Car c GROUP BY c.model.name")
    List<Map<String, Object>> getCarStatsForAllModels();
}
