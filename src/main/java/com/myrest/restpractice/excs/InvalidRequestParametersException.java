package com.myrest.restpractice.excs;

public class InvalidRequestParametersException extends GlobalException {

    public InvalidRequestParametersException(String requestParameterName) {
        super(400, "Invalid request parameter value = '" + requestParameterName + "'");
    }
}
