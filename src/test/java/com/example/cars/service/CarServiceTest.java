package com.example.cars.service;

import com.example.cars.dto.CarDTO;
import com.example.cars.exception.ModelIsNotActiveException;
import com.example.cars.exception.ModelNotFoundException;
import com.example.cars.model.Model;
import com.example.cars.repository.CarRepository;
import com.example.cars.repository.ModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private ModelRepository modelRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCarWithNonExistingModel() {
        Long modelId = 1L;
        when(modelRepository.findById(modelId)).thenReturn(Optional.empty());

        CarDTO carDTO = new CarDTO();
        carDTO.setModelId(modelId);
        carDTO.setColor("Black");
        carDTO.setPower(150);
        carDTO.setConsumption(7.5);
        carDTO.setCreationDate(LocalDate.of(2019,07,5));
        carDTO.setDrivable(true);

        assertThrows(ModelNotFoundException.class, () -> carService.createCar(carDTO, modelId));
    }

    @Test
    public void testCreateCarWithInactiveModel() {
        Long modelId = 1L;
        Model inactiveModel = new Model();
        inactiveModel.setId(modelId);
        inactiveModel.setActive(false);

        when(modelRepository.findById(modelId)).thenReturn(Optional.of(inactiveModel));

        CarDTO carDTO = new CarDTO();
        carDTO.setModelId(modelId);
        carDTO.setColor("Black");
        carDTO.setPower(150);
        carDTO.setConsumption(7.5);
        carDTO.setCreationDate(LocalDate.of(2019,07,5));
        carDTO.setDrivable(true);

        assertThrows(ModelIsNotActiveException.class, () -> carService.createCar(carDTO, modelId));
    }
}

