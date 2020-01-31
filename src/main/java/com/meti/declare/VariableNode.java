package com.meti.declare;

import com.meti.Node;

public class VariableNode implements Node {
    private final String value;

    public VariableNode(String value) {
        this.value = value;
    }

    @Override
    public String render() {
        return value;
    }
}
