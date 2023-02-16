package com.jakubowski.clinic.error;

public class IllegalDateRangeExeption extends RuntimeException{
    public IllegalDateRangeExeption() {
        super();
    }

    public IllegalDateRangeExeption(String message) {
        super(message);
    }
}
