package com.myrest.restpractice.excs;

public class MicrochipNotFoundException extends GlobalException {

    public MicrochipNotFoundException(long id) {
        super(404, "Microchip not found with id=" + id);
    }
}
