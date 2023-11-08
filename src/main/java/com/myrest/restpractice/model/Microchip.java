package com.myrest.restpractice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Microchip {

    private Long id;
    private String name;
    private String frameType;
    private Double voltage;
    private Integer price;
}