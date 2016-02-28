package com.mmontes.util.exception;

public class InvalidMetricException extends Exception{

    public InvalidMetricException(Object key) {
        super("Invalid Metric: "+ key);
    }
}
