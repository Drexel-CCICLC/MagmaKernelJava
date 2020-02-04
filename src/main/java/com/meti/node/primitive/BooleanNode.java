package com.meti.node.primitive;

import com.meti.node.Node;

public class BooleanNode implements Node {
    private final boolean isTrue;

    public BooleanNode(boolean isTrue) {
        this.isTrue = isTrue;
    }

    @Override
    public String render() {
        return (isTrue) ? "1" : "0";
    }
}
