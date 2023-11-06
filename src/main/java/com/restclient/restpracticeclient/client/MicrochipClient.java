package com.restclient.restpracticeclient.client;

import com.restclient.restpracticeclient.model.Microchip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "microchipClient", url = "http://localhost:8081/api")
public interface MicrochipClient {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Microchip getById(@PathVariable long id);

    @GetMapping("/")
    public List<Microchip> getAll(@RequestParam(name = "sortBy", required = false) String sortField);

    @GetMapping("/volt")
    public Long getAmountByVoltage(@RequestParam(name = "volt", required = false, defaultValue = "5.0") double voltage);

    @PostMapping("/")
    public List<Microchip> createNewByList(@RequestBody List<Microchip> microchipList);

    @PutMapping("/")
    public List<Microchip> replaceFrameType(
            @RequestParam(name = "formerFrameType") String formerFrameType,
            @RequestParam(name = "newFrameType") String newFrameType,
            @RequestParam(name = "printOnlyReplaced", required = false, defaultValue = "true") Boolean printOnlyReplaced);

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id);
}