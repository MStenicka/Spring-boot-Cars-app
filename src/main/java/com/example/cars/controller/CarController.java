package com.example.cars.controller;

import com.example.cars.dto.CarDTO;
import com.example.cars.exception.CarNotFoundException;
import com.example.cars.model.Car;
import com.example.cars.service.CarService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private static final Logger logger = LoggerFactory.getLogger("CarController.class");

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Gets a list of all cars.
     *
     * @return ResponseEntity containing the list of cars
     */
    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        try {
            List<CarDTO> cars = carService.getAllCars();
            logger.info("INFO: All cars retrieved successfully.");
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            logger.error("ERROR: An error occurred while retrieving all cars.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gets a car by its ID.
     *
     * @param id The ID of the car to retrieve
     * @return ResponseEntity containing the car data
     * @throws CarNotFoundException if the car with the given ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        CarDTO car = carService.getCarById(id);
        if (car != null) {
            logger.info("INFO: Car with ID " + id + " retrieved successfully.");
            return ResponseEntity.ok(car);
        } else {
            logger.error("ERROR: Car with ID " + id + " not found.");
            throw new CarNotFoundException();
        }
    }

    /**
     * Creates a new car.
     *
     * @param modelId The ID of the car's model
     * @param carDTO  The car data to be created
     * @return ResponseEntity containing the created car data
     */
    @PostMapping("/{modelId}")
    public ResponseEntity<CarDTO> createCar(@PathVariable Long modelId, @RequestBody @Valid CarDTO carDTO) {
        CarDTO createdCar = carService.createCar(carDTO, modelId);
        logger.info("SUCCESS: Car created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCar);
    }

    /**
     * Updates an existing car by its ID.
     *
     * @param id      The ID of the car to update
     * @param carDTO  The updated car data
     * @return ResponseEntity containing the updated car data
     * @throws CarNotFoundException if the car with the given ID is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long id, @RequestBody @Valid CarDTO carDTO) {
        CarDTO updatedCar = carService.updateCar(id, carDTO);
        if (updatedCar != null) {
            logger.info("SUCCESS: Car with ID " + id + " updated successfully.");
            return ResponseEntity.ok(updatedCar);
        } else {
            logger.error("ERROR: Car with ID " + id + " not found.");
            throw new CarNotFoundException();
        }
    }

    /**
     * Deletes a car by its ID.
     *
     * @param id The ID of the car to delete
     * @return ResponseEntity with a success message
     * @throws CarNotFoundException if the car with the given ID is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {
        if (carService.getCarById(id) != null) {
            carService.deleteCar(id);
            logger.info("SUCCESS: Car with ID " + id + " deleted successfully.");
            return ResponseEntity.ok().body("Car is deleted!");
        } else {
            logger.error("ERROR: Car with ID " + id + " not found.");
            throw new CarNotFoundException();
        }
    }

    /**
     * Gets statistics for cars of a specific model.
     *
     * @param modelName The name of the car model
     * @return ResponseEntity containing the car statistics for the specified model
     * @throws CarNotFoundException if the car statistics for the model are not found
     */
    @GetMapping("/stats/{modelName}")
    public ResponseEntity<Map<String, Long>> getCarStatsByModel(@PathVariable String modelName) {
        Map<String, Long> carStats = carService.getCarStatsByModel(modelName);
        if (carStats != null) {
            logger.info("INFO: Car statistics retrieved for model " + modelName + ".");
            return ResponseEntity.ok(carStats);
        } else {
            logger.error("ERROR: Car statistics not found for model " + modelName + ".");
            throw new CarNotFoundException();
        }
    }

    /**
     * Gets statistics for all car models.
     *
     * @return ResponseEntity containing the car statistics for all models
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Map<String, Object>>> getCarStatsForAllModels() {
        Map<String, Map<String, Object>> carStats = carService.getCarStatsForAllModels();
        logger.info("INFO: Car statistics retrieved for all models.");
        return ResponseEntity.ok(carStats);
    }

    /**
     * Imports cars from a JSON file.
     *
     * @param file The JSON file containing car data to import
     * @return ResponseEntity with a cars
     */
    @PostMapping("/import")
    public ResponseEntity<?> importCarsFromJson(@RequestParam("file") MultipartFile file) {
        try {
            List<Car> cars = carService.importCarsFromJson(file);
            logger.info("SUCCESS: Cars imported successfully.");
            return ResponseEntity.ok(cars);
        } catch (IOException e) {
            logger.error("ERROR: An error occurred while importing cars from the JSON file.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing cars from the JSON file.");
        }
    }
}

