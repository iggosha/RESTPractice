package com.myrest.restpractice.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myrest.restpractice.model.Microchip;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class GsonServiceImpl implements GsonService<Microchip> {

    Gson gson = new Gson();
    String fileName = "ms.json";

    public List<Microchip> getListFromJson() {
        try (FileReader fileReader = new FileReader(fileName)) {
            Type MicrochipListType = new TypeToken<ArrayList<Microchip>>() {}.getType();
            return gson.fromJson(fileReader, MicrochipListType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void putListToJson(List<Microchip> microchipList) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(microchipList, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
