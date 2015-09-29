package com.mmontes.util.exception;

public class GeometryParsingException extends Exception {

    public GeometryParsingException(String geometryText) {
        super("Error parsing geometry: "+geometryText);
    }
}
