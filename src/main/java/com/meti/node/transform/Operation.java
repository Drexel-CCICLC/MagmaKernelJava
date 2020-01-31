package com.meti.node.transform;

public enum Operation {
    ADD("+", "+");

    private final String from;
    private final String to;

    Operation(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
