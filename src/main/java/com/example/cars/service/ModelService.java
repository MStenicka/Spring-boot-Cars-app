package com.example.cars.service;

import com.example.cars.dto.ModelDTO;
import com.example.cars.exception.InvalidDataException;
import com.example.cars.exception.ManufacturerNotFoundException;
import com.example.cars.exception.ModelIsNotActiveException;
import com.example.cars.exception.ModelNotFoundException;
import com.example.cars.model.Manufacturer;
import com.example.cars.model.Model;
import com.example.cars.repository.ManufacturerRepository;
import com.example.cars.repository.ModelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {
    private final ModelRepository modelRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ModelService(ModelRepository modelRepository, ManufacturerRepository manufacturerRepository) {
        this.modelRepository = modelRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<ModelDTO> getAllModels() {
        List<Model> models = modelRepository.findAll();
        System.out.println(models);
        return models.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ModelDTO getModelById(Long id) {
        Model model = modelRepository.findById(id).orElse(null);
        return convertToDTO(model);
    }

    public ModelDTO createModel(ModelDTO modelDTO) {
        Manufacturer manufacturer = manufacturerRepository.findById(modelDTO.getManufacturerId()).orElse(null);
        if (manufacturer == null) {
            throw new ManufacturerNotFoundException();
        }
        if (modelDTO.getName() == null || modelDTO.getName().isEmpty() || modelDTO.getCategory() == null || modelDTO.getCategory().isEmpty()) {
            throw new InvalidDataException();
        }
        Model model = convertToEntity(modelDTO);
        Model savedModel = modelRepository.save(model);
        return convertToDTO(savedModel);
    }

    public ModelDTO updateModel(Long id, ModelDTO modelDTO) {
        Model existingModel = modelRepository.findById(id).orElse(null);
        if (existingModel != null) {
            Model updatedModel = convertToEntity(modelDTO);
            BeanUtils.copyProperties(updatedModel, existingModel, "id");
            Model savedModel = modelRepository.save(existingModel);
            return convertToDTO(savedModel);
        }
        return null;
    }

    public void deleteModel(Long id) {
        modelRepository.deleteById(id);
    }

    private ModelDTO convertToDTO(Model model) {
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setManufacturerId(model.getManufacturer().getId());
        BeanUtils.copyProperties(model, modelDTO);
        return modelDTO;
    }

    private Model convertToEntity(ModelDTO modelDTO) {
        Model model = new Model();
        if (modelDTO.getManufacturerId() != null) {
            Manufacturer manufacturer = manufacturerRepository.findById(modelDTO.getManufacturerId()).orElse(null);
            model.setManufacturer(manufacturer);
        }
        BeanUtils.copyProperties(modelDTO, model);
        return model;
    }
}
