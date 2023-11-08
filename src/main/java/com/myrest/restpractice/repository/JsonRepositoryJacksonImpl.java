package com.myrest.restpractice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrest.restpractice.model.Microchip;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonRepositoryJacksonImpl implements JsonRepository<Microchip> {

    ObjectMapper objectMapper = new ObjectMapper();
    String fileName = "ms.json";

    @Override
    public List<Microchip> getListFromJson() {
        try {
            return objectMapper.readValue(new File(fileName),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Microchip.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putListToJson(List<Microchip> objList) {
        try {
            objectMapper.writeValue(new File(fileName), objList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
