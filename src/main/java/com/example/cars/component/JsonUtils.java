package com.example.cars.component;

import com.example.cars.dto.CarDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JsonUtils {
    private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<CarDTO> readCarsFromJson(byte[] jsonData) throws IOException {
        TypeReference<List<CarDTO>> typeReference = new TypeReference<List<CarDTO>>() {};
        return objectMapper.readValue(jsonData, typeReference);
    }
}
