package com.mmontes.util.exception;

public class WikipediaServiceException extends Exception{
    public WikipediaServiceException() {
        super("There was an error requesting Wikipedia webservice");
    }
}
