package com.restclient.restpracticeclient.model;

import lombok.*;

@Data
@Builder
public class Microchip {
    @Builder.Default
    private Long id = null;
    @NonNull
    private String name;
    @NonNull
    private String frameType;
    @NonNull
    private Double voltage;
    @NonNull
    private Integer price;
}