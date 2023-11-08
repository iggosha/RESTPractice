package com.myrest.restpractice.controller;

import com.myrest.restpractice.model.Microchip;
import com.myrest.restpractice.service.MicrochipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class MicrochipController {

    @Autowired
    private MicrochipService microchipService;

    @GetMapping("/{id}")
    public ResponseEntity<Microchip> getById(@PathVariable Long id) {
        Microchip microchip = microchipService.getById(id);
        return getOkStatus(microchip);
    }

    @GetMapping("/")
    public ResponseEntity<List<Microchip>> getAll(@RequestParam(name = "sortBy") String sortField) {
        List<Microchip> microchipList = microchipService.getAll(sortField);
        return getOkStatus(microchipList);
    }

    @GetMapping("/volt")
    public ResponseEntity<Long> getAmountByVoltage(@RequestParam(name = "volt") Double voltage) {
        Long mcWithVoltageAmount = microchipService.getAmountByVoltage(voltage);
        return getOkStatus(mcWithVoltageAmount);
    }

    @PostMapping("/")
    public ResponseEntity<List<Microchip>> createNewByList(
            @RequestBody List<Microchip> microchipList) {
        List<Microchip> microchipListCurr = microchipService.createNewByList(microchipList);
        return getCreatedStatus(microchipListCurr);
    }

    @PutMapping("/")
    public ResponseEntity<List<Microchip>> replaceFrameType(
            @RequestParam(name = "formerFrameType") String formerFrameType,
            @RequestParam(name = "newFrameType") String newFrameType,
            @RequestParam(name = "printOnlyReplaced") Boolean printOnlyReplaced) {
        List<Microchip> microchipList = microchipService.replaceFrameType(formerFrameType, newFrameType, printOnlyReplaced);
        if (microchipList.isEmpty()) {
            return getNoContentStatus();
        }
        return getOkStatus(microchipList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        microchipService.deleteById(id);
        return getNoContentStatus();
    }

    private ResponseEntity<List<Microchip>> getNoContentStatus() {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private ResponseEntity<List<Microchip>> getOkStatus(List<Microchip> microchipList) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(microchipList);
    }

    private ResponseEntity<List<Microchip>> getCreatedStatus(List<Microchip> microchipList) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(microchipList);
    }

    private ResponseEntity<Microchip> getOkStatus(Microchip microchip) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(microchip);
    }

    private ResponseEntity<Long> getOkStatus(Long amount) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(amount);
    }
}

