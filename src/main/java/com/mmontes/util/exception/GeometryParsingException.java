package com.mmontes.util.exception;

public class GeometryParsingException extends Exception {

    public GeometryParsingException(String error) {
        super("Error parsing geometry: "+error);
    }
}
