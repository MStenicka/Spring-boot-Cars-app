package com.example.cars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Invalid data. Please check your request.");
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<String> handleCarNotFoundException(CarNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car/cars not found!");
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<String> handleModelNotFoundException(ModelNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Model/models not found!");
    }

    @ExceptionHandler(ManufacturerNotFoundException.class)
    public ResponseEntity<String> handleManufacturerNotFoundException(ManufacturerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manufacturer/manufacturers not found!");
    }

    @ExceptionHandler(ModelIsNotActiveException.class)
    public ResponseEntity<String> handleModelIsNotActiveException(ModelIsNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Model is not active!");
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid data, please check your request!");
    }
}
