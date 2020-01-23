package com.meti.primitive;

import com.meti.Node;

public class IntNode implements Node {
    private final int value;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public String render() {
        return value + "i";
    }
}
