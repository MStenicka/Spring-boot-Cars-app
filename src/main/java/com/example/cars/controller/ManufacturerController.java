package com.example.cars.controller;

import com.example.cars.dto.ManufacturerDTO;
import com.example.cars.exception.ManufacturerNotFoundException;
import com.example.cars.service.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    private static final Logger logger = LoggerFactory.getLogger(ManufacturerController.class);

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    /**
     * Retrieves a list of all manufacturers.
     *
     * @return ResponseEntity containing the list of manufacturers
     */
    @GetMapping
    public ResponseEntity<List<ManufacturerDTO>> getAllManufacturers() {
        try {
            List<ManufacturerDTO> manufacturers = manufacturerService.getAllManufacturers();
            logger.info("INFO: All manufacturers retrieved successfully.");
            return ResponseEntity.ok(manufacturers);
        } catch (Exception e) {
            logger.error("ERROR: An error occurred while retrieving all manufacturers.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a manufacturer by its ID.
     *
     * @param id The ID of the manufacturer to retrieve
     * @return ResponseEntity containing the manufacturer data
     * @throws ManufacturerNotFoundException if the manufacturer with the given ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getManufacturerById(@PathVariable Long id) {
        ManufacturerDTO manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer != null) {
            logger.info("INFO: Manufacturer with ID " + id + " retrieved successfully.");
            return ResponseEntity.ok(manufacturer);
        } else {
            logger.error("ERROR: Manufacturer with ID " + id + " not found.");
            throw new ManufacturerNotFoundException();
        }
    }

    /**
     * Creates a new manufacturer.
     *
     * @param manufacturerDTO The manufacturer data to be created
     * @return ResponseEntity containing the created manufacturer data
     */
    @PostMapping
    public ResponseEntity<ManufacturerDTO> createManufacturer(@RequestBody @Valid ManufacturerDTO manufacturerDTO) {
        ManufacturerDTO createdManufacturer = manufacturerService.createManufacturer(manufacturerDTO);
        logger.info("SUCCESS: Manufacturer created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdManufacturer);
    }

    /**
     * Updates an existing manufacturer by its ID.
     *
     * @param id               The ID of the manufacturer to update
     * @param manufacturerDTO  The updated manufacturer data
     * @return ResponseEntity containing the updated manufacturer data
     * @throws ManufacturerNotFoundException if the manufacturer with the given ID is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> updateManufacturer(@PathVariable Long id, @RequestBody @Valid ManufacturerDTO manufacturerDTO) {
        ManufacturerDTO updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturerDTO);
        if (updatedManufacturer != null) {
            logger.info("SUCCESS: Manufacturer with ID " + id + " updated successfully.");
            return ResponseEntity.ok(updatedManufacturer);
        } else {
            logger.error("ERROR: Manufacturer with ID " + id + " not found.");
            throw new ManufacturerNotFoundException();
        }
    }

    /**
     * Deletes a manufacturer by its ID.
     *
     * @param id The ID of the manufacturer to delete
     * @return ResponseEntity with a success message
     * @throws ManufacturerNotFoundException if the manufacturer with the given ID is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManufacturer(@PathVariable Long id) {
        ManufacturerDTO deletedManufacturer = manufacturerService.getManufacturerById(id);
        if (deletedManufacturer != null) {
            manufacturerService.deleteManufacturer(id);
            logger.info("SUCCESS: Manufacturer with ID " + id + " deleted successfully.");
            return ResponseEntity.ok().body("Manufacturer is deleted!");
        } else {
            logger.error("ERROR: Manufacturer with ID " + id + " not found.");
            throw new ManufacturerNotFoundException();
        }
    }
}
