package com.mmontes.util.exception;

public class AmazonServiceExeption extends Exception{
    public AmazonServiceExeption() {
        super("There was an Error requesting Amazon webservice");
    }
}
