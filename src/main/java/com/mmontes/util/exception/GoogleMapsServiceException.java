package com.mmontes.util.exception;

public class GoogleMapsServiceException extends Exception{
    public GoogleMapsServiceException() {
        super("There was an Error requesting Google Maps webservice");
    }
}
