package com.example.cars.component;

import com.example.cars.model.Car;
import com.example.cars.model.Manufacturer;
import com.example.cars.model.Model;
import com.example.cars.repository.CarRepository;
import com.example.cars.repository.ManufacturerRepository;
import com.example.cars.repository.ModelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Database implements CommandLineRunner {

    private final ManufacturerRepository manufacturerRepository;
    private final CarRepository carRepository;
    private final ModelRepository modelRepository;

    public Database(ManufacturerRepository manufacturerRepository, CarRepository carRepository, ModelRepository modelRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Přidání výrobců, modelů, automobilů do databáze pomocí konstruktoru, když je databáze prázdná
        if (manufacturerRepository.count() == 0 &&
                carRepository.count() == 0 &&
                modelRepository.count() == 0) {
            Manufacturer manufacturer1 = new Manufacturer(1L, "Audi", "Street 1", "Ingolstadt", "12345", "Germany");
            manufacturerRepository.save(manufacturer1);

            Manufacturer manufacturer2 = new Manufacturer(2L, "BMW", "Street 2", "Munchen", "67890", "Germany");
            manufacturerRepository.save(manufacturer2);

            Model model1 = new Model(1L, manufacturer1, "A4", "Sedan", "Medium", 1994, true);
            modelRepository.save(model1);
            Car car1 = new Car(1L, model1, "Black", 150, 7.5, LocalDate.of(2020, 7, 18), true);
            carRepository.save(car1);

            Model model2 = new Model(2L, manufacturer1, "Q5", "SUV", "High", 2020, true);
            modelRepository.save(model2);
            Car car2 = new Car(2L, model2, "White", 180, 8.2, LocalDate.of(2022, 7, 1), true);
            carRepository.save(car2);

            Model model3 = new Model(3L, manufacturer2, "X5", "SUV", "High", 1995, true);
            modelRepository.save(model3);
            Car car3 = new Car(3L, model3, "Grey", 180, 8.5, LocalDate.of(2012, 2, 15), false);
            carRepository.save(car3);

            System.out.println("Data were successfully seeded to the database.");
        }
    }
}
