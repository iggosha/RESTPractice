package com.myrest.restpractice.excs;

public class MicrochipExistsException extends GlobalException {

    public MicrochipExistsException(long id) {
        super(409, "Microchip already exists with id=" + id);
    }

    public MicrochipExistsException(String name) {
        super(409, "Microchip already exists with name=" + name);
    }
}
