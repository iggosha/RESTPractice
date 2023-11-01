package com.myrest.restpractice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Microchip {

    private long id;
    private String name;
    private String frameType;
    private double voltage;
    private int price;
}