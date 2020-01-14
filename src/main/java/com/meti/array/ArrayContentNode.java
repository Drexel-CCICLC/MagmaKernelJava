package com.meti.array;

import com.meti.Node;
import com.meti.Type;
import com.meti.integer.IntNode;

import java.util.List;

class ArrayContentNode implements Node {
    private final Type type;
    private final List<Node> elements;

    ArrayContentNode(Type type, List<Node> elements) {
        this.type = type;
        this.elements = elements;
    }

    @Override
    public String render() {
        Node size = new IntNode(elements.size());
        Node arraySize = new ArraySizeNode(type, size);

        return null;
    }
}
