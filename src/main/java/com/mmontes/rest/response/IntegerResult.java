package com.mmontes.rest.response;

public class IntegerResult {

    private int value;

    public IntegerResult(int maxWayPoints) {
        this.value = maxWayPoints;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
