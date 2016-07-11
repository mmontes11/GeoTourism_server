package com.mmontes.util.exception;

public class GoogleMapsAddressException extends Exception{
    public GoogleMapsAddressException() {
        super("There is no address asociated to this coordinates.");
    }
}
