package com.example.cars.service;

import com.example.cars.dto.ManufacturerDTO;
import com.example.cars.model.Manufacturer;
import com.example.cars.repository.ManufacturerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<ManufacturerDTO> getAllManufacturers() {
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        return manufacturers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ManufacturerDTO getManufacturerById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElse(null);
        return convertToDTO(manufacturer);
    }

    public ManufacturerDTO createManufacturer(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = convertToEntity(manufacturerDTO);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return convertToDTO(savedManufacturer);
    }

    public ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO manufacturerDTO) {
        Manufacturer existingManufacturer = manufacturerRepository.findById(id).orElse(null);
        if (existingManufacturer != null) {
            Manufacturer updatedManufacturer = convertToEntity(manufacturerDTO);
            BeanUtils.copyProperties(updatedManufacturer, existingManufacturer, "id");
            Manufacturer savedManufacturer = manufacturerRepository.save(existingManufacturer);
            return convertToDTO(savedManufacturer);
        }
        return null;
    }

    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }

    private ManufacturerDTO convertToDTO(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        BeanUtils.copyProperties(manufacturer, manufacturerDTO);
        return manufacturerDTO;
    }

    private Manufacturer convertToEntity(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = new Manufacturer();
        BeanUtils.copyProperties(manufacturerDTO, manufacturer);
        return manufacturer;
    }
}
