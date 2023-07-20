package com.example.cars.service;

import com.example.cars.component.JsonUtils;
import com.example.cars.dto.CarDTO;
import com.example.cars.exception.CarNotFoundException;
import com.example.cars.exception.InvalidDataException;
import com.example.cars.exception.ModelIsNotActiveException;
import com.example.cars.exception.ModelNotFoundException;
import com.example.cars.model.Car;
import com.example.cars.model.Model;
import com.example.cars.repository.CarRepository;
import com.example.cars.repository.ModelRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final JsonUtils jsonUtils;

    @Autowired
    public CarService(CarRepository carRepository, ModelRepository modelRepository, JsonUtils jsonUtils) {
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
        this.jsonUtils = jsonUtils;
    }

    public List<CarDTO> getAllCars() {
        List<Car> cars = carRepository.findAll();
        if (cars != null) {
        return cars.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        } else {
            throw new CarNotFoundException();
        }
    }

    public CarDTO getCarById(Long id) {
        Car car = carRepository.findById(id).orElse(null);
        if (car != null) {
            return convertToDTO(car);
        }
        return null;
    }

    public CarDTO createCar(CarDTO carDTO, Long modelId) {
        Model model = modelRepository.findById(modelId).orElse(null);
        if (model == null) {
            throw new ModelNotFoundException();
        }
        if (!model.isActive()) {
            throw new ModelIsNotActiveException();
        }
        if (carDTO.getColor() == null || carDTO.getColor().isEmpty()) {
            throw new InvalidDataException();
        }
        @Min(0)
        @Max(2000)
        int power = carDTO.getPower();
        if (power < 0 || power > 2000) {
            throw new InvalidDataException();
        }
        @NotNull
        Double consumption = carDTO.getConsumption();
        if (consumption == null) {
            throw new InvalidDataException();
        }

        Car car = convertToEntity(carDTO);
        car.setModel(model);
        Car savedCar = carRepository.save(car);
        return convertToDTO(savedCar);
    }

    public CarDTO updateCar(Long id, CarDTO carDTO) {
        Car existingCar = carRepository.findById(id).orElse(null);
        if (existingCar != null) {
            Car updatedCar = convertToEntity(carDTO);
            BeanUtils.copyProperties(updatedCar, existingCar, "id");
            Car savedCar = carRepository.save(existingCar);
            return convertToDTO(savedCar);
        }
        return null;
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    private CarDTO convertToDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setModelId(car.getModel().getId());
        BeanUtils.copyProperties(car, carDTO);
        return carDTO;
    }

    private Car convertToEntity(CarDTO carDTO) {
        Car car = new Car();
        if (carDTO.getModelId() != null) {
            Model model = modelRepository.findById(carDTO.getModelId()).orElse(null);
            car.setModel(model);
        }
        BeanUtils.copyProperties(carDTO, car);
        return car;
    }

    public Map<String, Long> getCarStatsByModel(String modelName) {
        return carRepository.getCarStatsByModel(modelName);
    }

    public Map<String, Map<String, Object>> getCarStatsForAllModels() {
        List<Map<String, Object>> carStats = carRepository.getCarStatsForAllModels();
        return carStats.stream()
                .collect(Collectors.toMap(
                        stat -> (String) stat.get("modelName"),
                        stat -> Map.of("drivableCars", stat.get("drivableCars"), "nonDrivableCars", stat.get("nonDrivableCars"))
                ));
    }

    public List<Car>  importCarsFromJson(MultipartFile file) throws IOException {
        try {
            byte[] jsonData = file.getBytes();
            List<CarDTO> importedCars = jsonUtils.readCarsFromJson(jsonData);
            List<Car> savedCars = new ArrayList<>();

            for (CarDTO carDTO : importedCars) {
                Car car = convertToEntity(carDTO);
                savedCars.add(carRepository.save(car));
            }
            return savedCars;
        } catch (IOException e) {
            throw new RuntimeException("Failed to import cars from the file.");
        }
    }
}
