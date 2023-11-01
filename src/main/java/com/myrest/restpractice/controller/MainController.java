package com.myrest.restpractice.controller;

import com.myrest.restpractice.entity.Microscheme;
import com.myrest.restpractice.excs.MicroschemeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class MainController {

    @Autowired
    GsonParser parser;

    @GetMapping("/{id}")
    public ResponseEntity<Microscheme> getById(@PathVariable long id) {
        List<Microscheme> microschemeList = parser.parse();
        Microscheme microscheme = microschemeList
                .stream()
                .filter(ms -> ms.getId() == id)
                .findFirst()
                .orElseThrow(() -> new MicroschemeNotFoundException(id));
        return ResponseEntity.status(HttpStatus.OK).body(microscheme);
    }

    @GetMapping("/")
    public ResponseEntity<List<Microscheme>> getAll() {
        List<Microscheme> microschemeList = parser.parse();
        return ResponseEntity.status(HttpStatus.OK).body(microschemeList);
    }
}

