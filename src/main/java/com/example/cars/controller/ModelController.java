package com.example.cars.controller;

import com.example.cars.dto.ModelDTO;
import com.example.cars.exception.ModelNotFoundException;
import com.example.cars.service.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/models")
public class ModelController {
    private final ModelService modelService;
    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * Retrieves a list of all car models.
     *
     * @return ResponseEntity containing the list of car models
     */
    @GetMapping
    public ResponseEntity<List<ModelDTO>> getAllModels() {
        try {
            List<ModelDTO> models = modelService.getAllModels();
            logger.info("INFO: All models retrieved successfully.");
            return ResponseEntity.ok(models);
        } catch (Exception e) {
            logger.error("ERROR: An error occurred while retrieving all models.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a car model by its ID.
     *
     * @param id The ID of the car model to retrieve
     * @return ResponseEntity containing the car model data
     * @throws ModelNotFoundException if the car model with the given ID is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModelDTO> getModelById(@PathVariable Long id) {
        ModelDTO model = modelService.getModelById(id);
        if (model != null) {
            logger.info("INFO: Model with ID " + id + " retrieved successfully.");
            return ResponseEntity.ok(model);
        } else {
            logger.error("ERROR: Model with ID " + id + " not found.");
            throw new ModelNotFoundException();
        }
    }

    /**
     * Creates a new car model.
     *
     * @param modelDTO The car model data to be created
     * @return ResponseEntity containing the created car model data
     */
    @PostMapping
    public ResponseEntity<ModelDTO> createModel(@RequestBody @Valid ModelDTO modelDTO) {
        ModelDTO createdModel = modelService.createModel(modelDTO);
        logger.info("SUCCESS: Model created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
    }

    /**
     * Updates an existing car model by its ID.
     *
     * @param id       The ID of the car model to update
     * @param modelDTO The updated car model data
     * @return ResponseEntity containing the updated car model data
     * @throws ModelNotFoundException if the car model with the given ID is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModelDTO> updateModel(@PathVariable Long id, @RequestBody @Valid ModelDTO modelDTO) {
        ModelDTO updatedModel = modelService.updateModel(id, modelDTO);
        if (updatedModel != null) {
            logger.info("SUCCESS: Model with ID " + id + " updated successfully.");
            return ResponseEntity.ok(updatedModel);
        } else {
            logger.error("ERROR: Model with ID " + id + " not found.");
            throw new ModelNotFoundException();
        }
    }

    /**
     * Deletes a car model by its ID.
     *
     * @param id The ID of the car model to delete
     * @return ResponseEntity with a success message
     * @throws ModelNotFoundException if the car model with the given ID is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModel(@PathVariable Long id) {
        if (modelService.getModelById(id) != null) {
            modelService.deleteModel(id);
            logger.info("SUCCESS: Model with ID " + id + " deleted successfully.");
            return ResponseEntity.ok().body("Model is deleted!");
        } else {
            logger.error("ERROR: Model with ID " + id + " not found.");
            throw new ModelNotFoundException();
        }
    }
}
