package com.mmontes.util.exception;

public class InvalidTIPUrlException extends Exception{

    public InvalidTIPUrlException(String urlType,String url) {
        super("The TIP "+urlType+" URL is invalid: "+url);
    }
}
