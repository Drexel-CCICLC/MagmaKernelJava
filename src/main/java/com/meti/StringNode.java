package com.meti;

public class StringNode implements Node {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public String render() {
        return "\"" + value + "\"";
    }
}
