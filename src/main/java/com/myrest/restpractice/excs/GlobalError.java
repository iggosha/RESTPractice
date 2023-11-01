package com.myrest.restpractice.excs;

import lombok.Data;

import java.util.Date;

@Data
public class GlobalError {
    private int status;
    private String message;
    private Date timestamp;

    public GlobalError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}