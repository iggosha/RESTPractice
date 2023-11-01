package com.myrest.restpractice.excs;

public class MicroschemeExistsException extends GlobalException {

    public MicroschemeExistsException(long id) {
        super(409, "Microscheme already exists with id=" + id);
    }

    public MicroschemeExistsException(String name) {
        super(409, "Microscheme already exists with name=" + name);
    }
}
