package com.myrest.restpractice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Microchip {

    private Long id;
    private String name;
    private String frameType;
    private Double voltage;
    private Integer price;
}