package com.myrest.restpractice.excs;

import lombok.Data;

import java.util.Date;

@Data
public abstract class GlobalException extends RuntimeException {

    protected int status;
    protected String message;
    protected Date time;

    public GlobalException(int status, String message) {
        this.status = status;
        this.message = message;
        this.time = new Date();
    }
}
