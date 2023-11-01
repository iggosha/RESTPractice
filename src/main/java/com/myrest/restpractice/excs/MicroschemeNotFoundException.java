package com.myrest.restpractice.excs;

public class MicroschemeNotFoundException extends GlobalException {

    public MicroschemeNotFoundException(long id) {
        super(404, "Microscheme not found with id=" + id);
    }
}
