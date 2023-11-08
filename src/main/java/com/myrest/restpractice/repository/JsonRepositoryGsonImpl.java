package com.myrest.restpractice.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myrest.restpractice.model.Microchip;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonRepositoryGsonImpl implements JsonRepository<Microchip> {

    Gson gson = new Gson();
    String fileName = "ms.json";

    @Override
    public List<Microchip> getListFromJson() {
        try (FileReader fileReader = new FileReader(fileName)) {
            Type MicrochipListType = new TypeToken<ArrayList<Microchip>>() {
            }.getType();
            return gson.fromJson(fileReader, MicrochipListType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putListToJson(List<Microchip> microchipList) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(microchipList, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
