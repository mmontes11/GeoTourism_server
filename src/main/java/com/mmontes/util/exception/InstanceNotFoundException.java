package com.mmontes.util.exception;

@SuppressWarnings("serial")
public class InstanceNotFoundException extends InstanceException {

    private static String message = "Instance not found";

    public InstanceNotFoundException(Object key, String className) {
        super(message, key, className);
    }

    public InstanceNotFoundException(){
        super(message,null,null);
    }
    
}
