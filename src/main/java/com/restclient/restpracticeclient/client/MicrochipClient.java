package com.restclient.restpracticeclient.client;

import com.restclient.restpracticeclient.model.Microchip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "microchipClient", url = "http://localhost:8081/api")
@Component
public interface MicrochipClient {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Microchip getById(@PathVariable Long id);

    @GetMapping("/")
    List<Microchip> getAll(@RequestParam(name = "sortBy") String sortField);

    @GetMapping("/volt")
    Long getAmountByVoltage(@RequestParam(name = "volt") Double voltage);

    @PostMapping("/")
    List<Microchip> createNewByList(@RequestBody List<Microchip> microchipList);

    @PutMapping("/")
    List<Microchip> replaceFrameType(@RequestParam(name = "formerFrameType") String formerFrameType,
                                     @RequestParam(name = "newFrameType") String newFrameType,
                                     @RequestParam(name = "printOnlyReplaced") Boolean printOnlyReplaced);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id);
}