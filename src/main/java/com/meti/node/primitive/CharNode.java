package com.meti.node.primitive;

import com.meti.node.Node;

public class CharNode implements Node {
    private final char value;

    public CharNode(char value) {
        this.value = value;
    }

    @Override
    public String render() {
        return "'" + value + "'";
    }
}
