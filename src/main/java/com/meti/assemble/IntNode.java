package com.meti.assemble;

import java.util.Map;

public class IntNode extends MapNode {
    public IntNode(int value) {
        this(Map.of(NodeProperty.VALUE, value));
    }

    public IntNode(Map<NodeProperty, Object> others) {
        super(others);
    }
}
