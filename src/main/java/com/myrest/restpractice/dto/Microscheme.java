package com.myrest.restpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Microscheme {
    private long id;
    private String name;
    private String frameType;
    private double voltage;
    private int price;
}
