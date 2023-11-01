package com.myrest.restpractice.controller;

import com.google.gson.Gson;
import com.myrest.restpractice.entity.MsContainer;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Component
@NoArgsConstructor
public class GsonParser {

    String fileName = "ms.json";

    public MsContainer parse() {
        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader(fileName)) {
            MsContainer microschemes = gson.fromJson(fileReader, MsContainer.class);
            return microschemes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
