package com.mmontes.util.exception;

public class InvalidRouteException extends Exception {
    public InvalidRouteException(String message){ super("Invalid Route: "+message); }
}
