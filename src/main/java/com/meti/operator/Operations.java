package com.meti.operator;

public enum Operations implements Operation {
    ADD("+"),
    MULTIPLY("*");

    private final String value;

    Operations(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
