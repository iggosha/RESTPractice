package com.myrest.restpractice.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myrest.restpractice.entity.Microscheme;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class GsonParser {

    String fileName = "ms.json";

    public List<Microscheme> parse() {
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(fileName)) {
            Type MicroschemeListType = new TypeToken<ArrayList<Microscheme>>() {}.getType();
            List<Microscheme> microschemes = gson.fromJson(fileReader, MicroschemeListType);
            return microschemes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
