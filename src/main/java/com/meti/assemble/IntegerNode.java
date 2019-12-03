package com.meti.assemble;

import java.util.Map;

public class IntegerNode extends MapNode {
    public IntegerNode(int value) {
        this(Map.of(NodeProperty.VALUE, value));
    }

    public IntegerNode(Map<NodeProperty, Object> others) {
        super(others);
    }
}
