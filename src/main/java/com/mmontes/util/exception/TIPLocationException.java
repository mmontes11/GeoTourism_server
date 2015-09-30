package com.mmontes.util.exception;

public class TIPLocationException extends Exception {
    public TIPLocationException() {
        super("The TIP location is incorrect. It should be located in a single City");
    }
}
