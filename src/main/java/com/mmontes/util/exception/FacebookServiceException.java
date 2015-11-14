package com.mmontes.util.exception;

public class FacebookServiceException extends Exception{
    public FacebookServiceException() {
        super("There was an Error requesting Facebook webservice");
    }
}
