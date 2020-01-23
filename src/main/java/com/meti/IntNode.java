package com.meti;

public class IntNode {
    private final int value;

    public IntNode(int value) {
        this.value = value;
    }

    public String render() {
        return String.valueOf(value);
    }
}
