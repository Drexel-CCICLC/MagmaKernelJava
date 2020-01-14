package com.meti.operator;

import com.meti.Node;
import com.meti.Type;

public class SizeOfNode implements Node {
    private final Type type;

    public SizeOfNode(Type type) {
        this.type = type;
    }

    @Override
    public String render() {
        return "sizeof(" + type.render() + ")";
    }
}
